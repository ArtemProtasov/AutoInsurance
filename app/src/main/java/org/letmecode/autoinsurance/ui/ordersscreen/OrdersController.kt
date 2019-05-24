package org.letmecode.autoinsurance.ui.ordersscreen

import com.airbnb.epoxy.EpoxyController
import org.letmecode.autoinsurance.data.Policy
import org.letmecode.autoinsurance.ui.ordersscreen.model.OrdersModel_
import java.util.*

class OrdersController(val ordersListener: OrdersListener) : EpoxyController() {

    private var policyList: List<Policy>? = null

    override fun buildModels() {
        policyList?.let {
            for (policy: Policy in it)
                OrdersModel_()
                        .policy(policy)
                        .ordersListener(ordersListener)
                        .id(UUID.randomUUID().toString())
                        .addTo(this)
        }
    }

    fun setupPolicyList(policyList: List<Policy>) {
        this.policyList = policyList
        requestModelBuild()
    }

    interface OrdersListener {
        fun onClickOrder(policy: Policy)
    }
}