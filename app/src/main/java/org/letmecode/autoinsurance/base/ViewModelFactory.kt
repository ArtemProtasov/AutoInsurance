package org.letmecode.autoinsurance.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.letmecode.autoinsurance.ui.auth.signup.SignUpViewModel
import org.letmecode.autoinsurance.ui.newpolicy.NewPolicyViewModel
import org.letmecode.autoinsurance.ui.settingsscreen.SettingsViewModel

/**
 * Created by Artem Protasov (zippe.inc@gmail.com) on [14-05-2019].
 */
class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return when {
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> SignUpViewModel() as T
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> SettingsViewModel() as T
            modelClass.isAssignableFrom(NewPolicyViewModel::class.java) -> NewPolicyViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }

    }


}