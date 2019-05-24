package org.letmecode.autoinsurance.ui.mypolicy

import com.google.firebase.database.DatabaseReference
import durdinapps.rxfirebase2.DataSnapshotMapper
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import org.letmecode.autoinsurance.base.BaseViewModel
import org.letmecode.autoinsurance.base.SingleLiveEvent
import org.letmecode.autoinsurance.data.Policy
import org.letmecode.autoinsurance.type.PolicyFields

class MyPolicyViewModel : BaseViewModel() {

    val policyListResponse = SingleLiveEvent<List<Policy>>()

    fun requesPolicyList(databaseReference: DatabaseReference?, userUID: String, query: String) {
        compositeDisposable.add(RxFirebaseDatabase.observeSingleValueEvent(databaseReference?.child(query)?.child(userUID)!!,
                DataSnapshotMapper.listOf(Policy::class.java, Function {
                    val itHashMap = it.value as HashMap<*, *>
                    return@Function Policy(
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
                            itHashMap[PolicyFields.userUID.field].toString(),
                            itHashMap[PolicyFields.price.field].toString())
                }))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer {
                    policyListResponse.postValue(it)
                }, errorLoadingConsumer()))
    }


}