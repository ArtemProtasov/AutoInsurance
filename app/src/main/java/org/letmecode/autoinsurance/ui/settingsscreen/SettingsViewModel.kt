package org.letmecode.autoinsurance.ui.settingsscreen

import android.net.Uri
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.firebase.ui.auth.AuthUI
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.StorageReference
import durdinapps.rxfirebase2.RxFirebaseStorage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.letmecode.autoinsurance.base.BaseViewModel
import org.letmecode.autoinsurance.base.SingleLiveEvent

/**
 * Created by Artem Protasov (zippe.inc@gmail.com) on [14-05-2019].
 */
class SettingsViewModel : BaseViewModel() {

    val logOutResponse = SingleLiveEvent<Boolean>()
    val changeDisplayNameResponse = SingleLiveEvent<Boolean>()
    val changeUserPasswordResponse = SingleLiveEvent<Boolean>()
    val userPhotoUriResponse = SingleLiveEvent<Uri>()
    private val changeUserPhotoResponse = SingleLiveEvent<Boolean>()

    fun sendEmailVerification(activity: FragmentActivity, firebaseUser: FirebaseUser, button: ImageView) {
        firebaseUser.sendEmailVerification()
            .addOnCompleteListener(activity) {
                button.isEnabled = false
                if (it.isSuccessful) {
                    Snackbar.make(button, "Verification email sent to ${firebaseUser.email}.", Snackbar.LENGTH_LONG)
                        .show()
                } else {
                    Snackbar.make(button, "Failed to send verification email.", Snackbar.LENGTH_LONG).show()
                }
            }
    }

    fun logOutUser(activity: FragmentActivity, authUi: AuthUI) {
        authUi.signOut(activity)
            .addOnCompleteListener {
                logOutResponse.postValue(it.isSuccessful)
            }
    }

    fun changeUserName(firebaseUser: FirebaseUser, newDisplayName: String) {
        firebaseUser.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(newDisplayName).build())
            .addOnCompleteListener {
                changeDisplayNameResponse.postValue(it.isSuccessful)
            }
    }

    fun changeUserPassword(firebaseUser: FirebaseUser, oldPassword: String, newPassword: String) {
        val authCredential = EmailAuthProvider.getCredential(firebaseUser.email!!, oldPassword)
        firebaseUser.reauthenticate(authCredential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    firebaseUser.updatePassword(newPassword)
                        .addOnCompleteListener { itUpdate ->
                            if (itUpdate.isSuccessful) {
                                changeUserPasswordResponse.postValue(true)
                            } else {
                                changeUserPasswordResponse.postValue(false)
                            }
                        }
                } else {
                    changeUserPasswordResponse.postValue(false)
                }
            }
    }

    fun uploadUserPhoto(storageReference: StorageReference, filePath: Uri, userUID: String) {
        compositeDisposable.add(
            RxFirebaseStorage.putFile(storageReference.child("images/$userUID"), filePath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(progressLoadingCunsumer())
                .subscribe(Consumer {
                    changeUserPhotoResponse.postValue(true)
                    terminateProgress().run()
                }, errorLoadingConsumer())
        )
    }

    fun downloadUserPhoto(storageReference: StorageReference) {
        compositeDisposable.add(
            RxFirebaseStorage.getDownloadUrl(storageReference)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(progressLoadingCunsumer())
                .subscribe(Consumer {
                    userPhotoUriResponse.postValue(it)
                    terminateProgress().run()
                }, errorLoadingConsumer())
        )
    }

}