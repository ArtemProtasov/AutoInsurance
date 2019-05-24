package org.letmecode.autoinsurance.ui.auth.signin

import com.google.firebase.database.DatabaseReference
import durdinapps.rxfirebase2.DataSnapshotMapper
import durdinapps.rxfirebase2.RxFirebaseDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.letmecode.autoinsurance.base.BaseViewModel
import org.letmecode.autoinsurance.base.SingleLiveEvent

class SignInViewModel : BaseViewModel() {

    val userTypeResponse = SingleLiveEvent<String>()

    fun requestUserType(databaseReference: DatabaseReference?, userUID: String, query: String) {
        compositeDisposable.add(
            RxFirebaseDatabase.observeSingleValueEvent(
                databaseReference?.child(query)?.child(userUID)!!,
                DataSnapshotMapper.of(String::class.java)
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(progressLoadingCunsumer())
                .subscribe(Consumer {
                    userTypeResponse.postValue(it)
                    terminateProgress().run()
                }, errorLoadingConsumer())
        )
    }

}