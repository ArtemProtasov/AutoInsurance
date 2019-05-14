package org.letmecode.autoinsurance.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by Artem Protasov (zippe.inc@gmail.com) on [14-05-2019].
 */
class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return throw IllegalArgumentException("Unknown ViewModel class")

    }


}