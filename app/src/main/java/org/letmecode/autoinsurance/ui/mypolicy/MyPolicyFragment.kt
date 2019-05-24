package org.letmecode.autoinsurance.ui.mypolicy

import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.fragment_my_policy.*
import org.letmecode.autoinsurance.R
import org.letmecode.autoinsurance.base.BaseFragment
import org.letmecode.autoinsurance.data.Policy
import org.letmecode.autoinsurance.ui.mypolicy.bottomsheet.PolicyBottomDialogFragment


class MyPolicyFragment : BaseFragment(), MyPolicyController.MyPolicyListener, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var viewModel: MyPolicyViewModel
    private lateinit var controller: MyPolicyController

    override fun contentResource(): Int {
        return R.layout.fragment_my_policy
    }

    override fun setupFirebaseAuth(): FirebaseAuth? {
        return FirebaseAuth.getInstance()
    }

    override fun setupDatabase(): DatabaseReference? {
        return firebaseDatabase.reference
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel = getViewModel(this, MyPolicyViewModel::class.java)

        viewModel.policyListResponse.observe(this, observerPolicyList())
        viewModel.errorLoading.observe(this, observerErrorLoading())
    }

    override fun setupView() {
        setupController()

        swipeRefreshLayoutPolicy.setOnRefreshListener(this)

        swipeRefreshLayoutPolicy.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)


        viewModel.requesPolicyList(databaseReference, firebaseAuth?.currentUser?.uid!!, "policy")
    }

    private fun setupController() {
        controller = MyPolicyController(this)
        epoxyRecyclerViewMyPolicy.setController(controller)
    }

    private fun observerPolicyList(): Observer<in List<Policy>?> {
        return Observer { policyList ->
            policyList?.let {
                controller.setupPolicyList(it)
                swipeRefreshLayoutPolicy.isRefreshing = false
            }
        }
    }

    override fun onClickPolicy(policy: Policy) {
        setupAndShowBottomSheetDialog(policy)
    }

    override fun onRefresh() {
        viewModel.requesPolicyList(databaseReference, firebaseAuth?.currentUser?.uid!!, "policy")
    }

    private fun setupAndShowBottomSheetDialog(policy: Policy) {
        val policyBottomDialogFragment = PolicyBottomDialogFragment.newInstance(policy)
        fragmentManager?.let {
            policyBottomDialogFragment.show(it, "Custom bottom sheet")
        }
    }

}