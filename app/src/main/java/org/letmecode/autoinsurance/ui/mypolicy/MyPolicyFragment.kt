package org.letmecode.autoinsurance.ui.mypolicy

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.fragment_my_policy.*
import kotlinx.android.synthetic.main.progress_layout.*
import org.letmecode.autoinsurance.R
import org.letmecode.autoinsurance.base.BaseFragment
import org.letmecode.autoinsurance.data.Policy
import org.letmecode.autoinsurance.ui.mypolicy.bottomsheet.PolicyBottomDialogFragment


class MyPolicyFragment : BaseFragment(), MyPolicyController.MyPolicyListener {

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

    override fun getProgress(): View? {
        return progressView
    }

    override fun getViewGroup(): ViewGroup? {
        return rootView
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel = getViewModel(this, MyPolicyViewModel::class.java)

        viewModel.policyListResponse.observe(this, observerPolicyList())
        viewModel.errorLoading.observe(this, observerErrorLoading())
        viewModel.progressLoading.observe(this, observerProgressLoading())
    }

    override fun setupView() {
        setupController()

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
            }
        }
    }

    override fun onClickPolicy(policy: Policy) {
        setupAndShowBottomSheetDialog(policy)
    }

    private fun setupAndShowBottomSheetDialog(policy: Policy) {
        val policyBottomDialogFragment = PolicyBottomDialogFragment.newInstance(policy)
        fragmentManager?.let {
            policyBottomDialogFragment.show(it, "Custom bottom sheet")
        }
    }

}