package org.letmecode.autoinsurance.ui.ordersscreen

import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.fragment_orders.*
import org.letmecode.autoinsurance.R
import org.letmecode.autoinsurance.base.BaseFragment
import org.letmecode.autoinsurance.data.Policy
import org.letmecode.autoinsurance.ui.ordersscreen.bottomsheet.OrdersBottomDialogFragment

/**
 * Created by Artem Protasov (zippe.inc@gmail.com) on [14-05-2019].
 */
class OrdersFragment : BaseFragment(), OrdersController.OrdersListener, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var viewModel: OrdersViewModel
    private lateinit var controller: OrdersController

    override fun contentResource(): Int {
        return R.layout.fragment_orders
    }

    override fun setupFirebaseAuth(): FirebaseAuth? {
        return FirebaseAuth.getInstance()
    }

    override fun setupDatabase(): DatabaseReference? {
        return firebaseDatabase.reference
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel = getViewModel(this, OrdersViewModel::class.java)

        viewModel.errorLoading.observe(this, observerErrorLoading())
        viewModel.policyListResponse.observe(this, observerPolicyList())
    }

    override fun setupView() {
        setupController()

        swipeRefreshLayoutOrders.setOnRefreshListener(this)
        swipeRefreshLayoutOrders.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)

        viewModel.requestAllPolicy(databaseReference, "policy")
    }

    private fun setupController() {
        controller = OrdersController(this)
        epoxyRecyclerViewOrders.setController(controller)
    }

    private fun observerPolicyList(): Observer<in List<Policy>?> {
        return Observer { policyList ->
            policyList?.let {
                controller.setupPolicyList(it)
                swipeRefreshLayoutOrders.isRefreshing = false
            }
        }
    }

    override fun onClickOrder(policy: Policy) {
        setupAndShowBottomSheetDialog(policy)
    }

    override fun onRefresh() {
        viewModel.requestAllPolicy(databaseReference, "policy")
    }

    private fun setupAndShowBottomSheetDialog(policy: Policy) {
        val ordersBottomDialogFragment = OrdersBottomDialogFragment.newInstance(policy)
        fragmentManager?.let {
            ordersBottomDialogFragment.show(it, "Orders bottom sheet")
        }
    }

}