package org.letmecode.autoinsurance.ui.ordersscreen

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import org.letmecode.autoinsurance.base.BaseViewModel
import org.letmecode.autoinsurance.base.SingleLiveEvent
import org.letmecode.autoinsurance.data.Policy
import org.letmecode.autoinsurance.type.PolicyFields

/**
 * Created by Artem Protasov (zippe.inc@gmail.com) on [14-05-2019].
 */
class OrdersViewModel : BaseViewModel() {

    val policyListResponse = SingleLiveEvent<List<Policy>>()

    fun requestAllPolicy(databaseReference: DatabaseReference?, query: String) {
        databaseReference?.child(query)?.orderByKey()?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                errorLoadingConsumer()
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.value != null) {
                    val allData = p0.value as HashMap<String, HashMap<String, HashMap<String, String>>>
                    val allOrders = emptyList<Policy>().toMutableList()

                    for (userValue in allData.values) {
                        for (itHashMap in userValue.values) {
                            val policy = Policy(
                                    itHashMap[PolicyFields.policyID.field].toString(),
                                    itHashMap[PolicyFields.surname.field].toString(),
                                    itHashMap[PolicyFields.firstName.field].toString(),
                                    itHashMap[PolicyFields.patronymic.field].toString(),
                                    itHashMap[PolicyFields.dateOfBirth.field].toString(),
                                    itHashMap[PolicyFields.gender.field].toString(),
                                    itHashMap[PolicyFields.documentType.field].toString(),
                                    itHashMap[PolicyFields.documentSeries.field].toString(),
                                    itHashMap[PolicyFields.documentNumber.field].toString(),
                                    itHashMap[PolicyFields.documentDateOfIssue.field].toString(),
                                    itHashMap[PolicyFields.documentIssueBy.field].toString(),
                                    itHashMap[PolicyFields.placeOfResidence.field].toString(),
                                    itHashMap[PolicyFields.autoDocumentType.field].toString(),
                                    itHashMap[PolicyFields.autoDocumentSeries.field].toString(),
                                    itHashMap[PolicyFields.autoDocumentNumber.field].toString(),
                                    itHashMap[PolicyFields.autoBrand.field].toString(),
                                    itHashMap[PolicyFields.autoModel.field].toString(),
                                    itHashMap[PolicyFields.autoYearOfIssue.field].toString(),
                                    itHashMap[PolicyFields.autoRegNumber.field].toString(),
                                    itHashMap[PolicyFields.autoIDNumberType.field].toString(),
                                    itHashMap[PolicyFields.autoIDNumber.field].toString(),
                                    itHashMap[PolicyFields.autoPurposeOfUsing.field].toString(),
                                    itHashMap[PolicyFields.autoPower.field].toString(),
                                    itHashMap[PolicyFields.autoNumberOfDiagnosticCard.field].toString(),
                                    itHashMap[PolicyFields.autoDateDiagnosticCardOfIssue.field].toString(),
                                    itHashMap[PolicyFields.autoDateDiagnosticCardValidUntil.field].toString(),
                                    itHashMap[PolicyFields.approval.field].toString(),
                                    itHashMap[PolicyFields.date.field].toString(),
                                    itHashMap[PolicyFields.userUID.field].toString()
                            )
                            allOrders.add(policy)
                        }
                    }
                    policyListResponse.postValue(allOrders)
                }
            }
        })
    }

}