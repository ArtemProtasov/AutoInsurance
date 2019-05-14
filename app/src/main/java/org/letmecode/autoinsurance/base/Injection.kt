package org.letmecode.autoinsurance.base

/**
 * Created by Artem Protasov (zippe.inc@gmail.com) on [14-05-2019].
 */
object Injection {

    @JvmStatic
    fun provideViewModelFactory(): ViewModelFactory {
        return ViewModelFactory()
    }

}