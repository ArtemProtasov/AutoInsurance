package org.letmecode.autoinsurance.ui.auth.signup

import com.google.firebase.database.DatabaseReference
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.functions.Action
import org.letmecode.autoinsurance.base.BaseViewModel
import org.letmecode.autoinsurance.base.SingleLiveEvent

class SignUpViewModel : BaseViewModel() {

    val addUserType = SingleLiveEvent<Boolean>()

    fun saveUserType(databaseReference: DatabaseReference?, userType: String, userUID: String) {
        compositeDisposable.add(
                RxFirebaseDatabase.setValue(
                        databaseReference?.child("userType")?.child(userUID)!!,
                        userType
                )
                        .subscribe(Action {
                            addUserType.postValue(true)
                        }, errorLoadingConsumer())
        )
    }

}