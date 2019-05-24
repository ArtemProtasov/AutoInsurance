package org.letmecode.autoinsurance.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.letmecode.autoinsurance.ui.auth.signin.SignInViewModel
import org.letmecode.autoinsurance.ui.auth.signup.SignUpViewModel
import org.letmecode.autoinsurance.ui.mypolicy.MyPolicyViewModel
import org.letmecode.autoinsurance.ui.newpolicy.NewPolicyViewModel
import org.letmecode.autoinsurance.ui.ordersscreen.OrdersViewModel
import org.letmecode.autoinsurance.ui.ordersscreen.bottomsheet.OrdersBottomDialogViewModel
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
            modelClass.isAssignableFrom(MyPolicyViewModel::class.java) -> MyPolicyViewModel() as T
            modelClass.isAssignableFrom(SignInViewModel::class.java) -> SignInViewModel() as T
            modelClass.isAssignableFrom(OrdersViewModel::class.java) -> OrdersViewModel() as T
            modelClass.isAssignableFrom(OrdersBottomDialogViewModel::class.java) -> OrdersBottomDialogViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }

    }


}