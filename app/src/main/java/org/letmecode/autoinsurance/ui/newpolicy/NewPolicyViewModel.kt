package org.letmecode.autoinsurance.ui.newpolicy

import com.google.firebase.database.DatabaseReference
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.functions.Action
import org.letmecode.autoinsurance.base.BaseViewModel
import org.letmecode.autoinsurance.base.SingleLiveEvent
import org.letmecode.autoinsurance.data.Policy

class NewPolicyViewModel : BaseViewModel() {

    val newPolicyResponse = SingleLiveEvent<Policy>()

    fun sendNewPolice(databaseReference: DatabaseReference?, policy: Policy, policyID: String, userUID: String) {
        compositeDisposable.add(
                RxFirebaseDatabase.setValue(databaseReference?.child("policy")?.child(userUID)?.child(policyID)!!, policy)
                        .doOnSubscribe(progressLoadingCunsumer())
                        .subscribe(Action {
                            newPolicyResponse.postValue(policy)
                            terminateProgress().run()
                        }, errorLoadingConsumer())
        )
    }
}