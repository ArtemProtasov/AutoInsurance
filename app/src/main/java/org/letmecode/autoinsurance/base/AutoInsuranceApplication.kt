package org.letmecode.autoinsurance.base

import android.app.Application
import com.google.firebase.FirebaseApp

/**
 * Created by Artem Protasov (zippe.inc@gmail.com) on [14-05-2019].
 */
class AutoInsuranceApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }

}