package org.letmecode.autoinsurance.ui.settingsscreen

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.firebase.ui.auth.AuthUI
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.progress_layout.*
import org.letmecode.autoinsurance.R
import org.letmecode.autoinsurance.base.BaseFragment
import org.letmecode.autoinsurance.base.GlideApp
import org.letmecode.autoinsurance.type.UserType

/**
 * Created by Artem Protasov (zippe.inc@gmail.com) on [14-05-2019].
 */
class SettingsFragment : BaseFragment() {

    private val PICK_IMAGE_REQUEST = 71
    private var viewModel: SettingsViewModel? = null
    private lateinit var navController: NavController

    override fun contentResource(): Int {
        return R.layout.fragment_settings
    }

    override fun setupFirebaseAuth(): FirebaseAuth? {
        return FirebaseAuth.getInstance()
    }

    override fun getProgress(): View? {
        return progressView
    }

    override fun getViewGroup(): ViewGroup? {
        return rootView
    }

    override fun setupViewModel() {
        viewModel = getViewModel(this, SettingsViewModel::class.java)

        viewModel?.logOutResponse?.observe(this, observerLogOut())
        viewModel?.changeDisplayNameResponse?.observe(this, observerChangeDisplayName())
        viewModel?.changeUserPasswordResponse?.observe(this, observerChangePassword())
        viewModel?.userPhotoUriResponse?.observe(this, observerUserPhotoUri())
        viewModel?.errorLoading?.observe(this, observerErrorLoading())
        viewModel?.progressLoading?.observe(this, observerProgressLoading())
    }

    override fun setupView() {

        arguments?.let {
            navController = if(it.getString("userType") == UserType.USER.userType) {
                Navigation.findNavController(requireActivity(), R.id.navHostFragmentUser)
            } else {
                Navigation.findNavController(requireActivity(), R.id.navHostFragmentManager)
            }
        }
        GlideApp.with(this)
            .load("")
            .centerCrop()
            .placeholder(R.drawable.ic_no_photo)
            .error(R.drawable.ic_no_photo)
            .into(userPhoto)

        updateUserName()

        userEmail.text = if (TextUtils.isEmpty(firebaseAuth?.currentUser?.email)) {
            "Email не найден"
        } else {
            firebaseAuth?.currentUser?.email.toString()
        }

        if (firebaseAuth?.currentUser?.isEmailVerified!!) {
            userEmailIsVerified.setImageResource(R.drawable.ic_user_verified)
        } else {
            userEmailIsVerified.setImageResource(R.drawable.ic_user_unverified)
        }

        userEmailIsVerified.setOnClickListener {
            viewModel?.sendEmailVerification(activity!!, firebaseAuth?.currentUser!!, userEmailIsVerified)
        }

        userEmailIsVerified.setOnClickListener {
            viewModel?.sendEmailVerification(activity!!, firebaseAuth?.currentUser!!, userEmailIsVerified)
        }

        changeNameButton.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.alert_dialog_change_name, null)
            MaterialAlertDialogBuilder(activity)
                .setView(view)
                .setPositiveButton(getString(R.string.button_ok)) { _, _ ->
                    viewModel?.changeUserName(
                        firebaseAuth?.currentUser!!,
                        view.findViewById<EditText>(R.id.inputDisplayName).text.toString()
                    )
                }
                .setNegativeButton(getString(R.string.button_cancel)) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
                .create().show()
        }

        changePasswordButton.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.alert_dialog_change_password, null)
            MaterialAlertDialogBuilder(activity)
                .setView(view)
                .setPositiveButton(getString(R.string.button_ok)) { _, _ ->
                    val oldPassword = view.findViewById<EditText>(R.id.inputOldPassword).text.toString()
                    val newPassword = view.findViewById<EditText>(R.id.inputNewPassword).text.toString()
                    val repeatNewPassword = view.findViewById<EditText>(R.id.inputRepeatNewPassword).text.toString()

                    if (newPassword == repeatNewPassword) {
                        viewModel?.changeUserPassword(firebaseAuth?.currentUser!!, oldPassword, newPassword)
                    } else {
                        Snackbar.make(
                            rootView,
                            "Пароли не совпадают!",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
                .setNegativeButton(getString(R.string.button_cancel)) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
                .create().show()
        }

        logOutButton.setOnClickListener {
            viewModel?.logOutUser(activity!!, AuthUI.getInstance())
        }

        userPhoto.setOnClickListener {
            chooseUserPhoto()
        }

//        viewModel?.downloadUserPhoto(FirebaseStorage.getInstance().reference.child("images").child(firebaseAuth?.currentUser?.uid!!))
    }

    private fun updateUserName() {
        userName.text = if (TextUtils.isEmpty(firebaseAuth?.currentUser?.displayName)) {
            "Имя пользователя не найдено"
        } else {
            firebaseAuth?.currentUser?.displayName.toString()
        }
    }

    private fun observerLogOut(): Observer<in Boolean?> {
        return Observer {
            arguments?.let {
                if(it.getString("userType") == UserType.USER.userType) {
                    navController.navigate(R.id.action_settingsFragment_to_authActivity)
                } else {
                    navController.navigate(R.id.action_settingsFragment2_to_authActivity2)
                }
            } }
    }

    private fun observerChangeDisplayName(): Observer<in Boolean?> {
        return Observer {
            Snackbar.make(rootView, "Ваш профиль обновлен", Snackbar.LENGTH_LONG).show()
            updateUserName()
        }
    }

    private fun observerChangePassword(): Observer<in Boolean?> {
        return Observer {
            if (it!!) {
                Snackbar.make(rootView, "Пароль изменен!", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(rootView, "Произошла ошибка при изменении пароля!", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun observerUserPhotoUri(): Observer<in Uri?> {
        return Observer {
            GlideApp.with(this)
                .load(it)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_no_photo)
                .error(R.drawable.ic_no_photo)
                .into(userPhoto)
        }
    }

    private fun chooseUserPhoto() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Photo"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val firePath = data.data
            GlideApp.with(this)
                .load(firePath)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_no_photo)
                .error(R.drawable.ic_no_photo)
                .into(userPhoto)

            viewModel?.uploadUserPhoto(
                FirebaseStorage.getInstance().reference,
                firePath!!,
                firebaseAuth?.currentUser?.uid!!
            )

        }
    }

}