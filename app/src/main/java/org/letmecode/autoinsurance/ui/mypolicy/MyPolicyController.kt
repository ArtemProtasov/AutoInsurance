package org.letmecode.autoinsurance.ui.mypolicy

import com.airbnb.epoxy.EpoxyController
import org.letmecode.autoinsurance.data.Policy
import org.letmecode.autoinsurance.ui.mypolicy.model.PolicyModel_
import java.util.*

class MyPolicyController(val myPolicyListener: MyPolicyListener) : EpoxyController() {

    private var policyList: List<Policy>? = null

    override fun buildModels() {
        policyList?.let {

            for (policy: Policy in it) {
                PolicyModel_()
                        .policy(policy)
                        .myPolicyListener(myPolicyListener)
                        .id(UUID.randomUUID().toString())
                        .addTo(this)
            }

        }
    }

    fun setupPolicyList(policyList: List<Policy>) {
        this.policyList = policyList
        requestModelBuild()
    }


    interface MyPolicyListener {
        fun onClickPolicy(policy: Policy)
    }
}