package org.letmecode.autoinsurance.ui.ordersscreen.bottomsheet

import com.google.firebase.database.DatabaseReference
import org.letmecode.autoinsurance.base.BaseViewModel
import org.letmecode.autoinsurance.base.SingleLiveEvent

class OrdersBottomDialogViewModel : BaseViewModel() {

    val serverResponse = SingleLiveEvent<Boolean>()

    fun requestDataChange(
            databaseReference: DatabaseReference?,
            userUID: String,
            policyID: String,
            query: String,
            fieldApproval: String,
            valueApproval: String,
            fieldPrice: String,
            valuePrice: String,
            fieldOfficeAddress: String,
            valueOfficeAddress: String,
            fragment: OrdersBottomDialogFragment) {
        databaseReference?.child(query)?.child(userUID)?.child(policyID)?.child(fieldApproval)?.setValue(valueApproval)
        databaseReference?.child(query)?.child(userUID)?.child(policyID)?.child(fieldPrice)?.setValue(valuePrice)
        databaseReference?.child(query)?.child(userUID)?.child(policyID)?.child(fieldOfficeAddress)?.setValue(valueOfficeAddress)
        fragment.dismiss()
    }

}