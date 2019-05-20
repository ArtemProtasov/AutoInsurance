package org.letmecode.autoinsurance.ui.auth

import androidx.navigation.NavController
import androidx.navigation.Navigation
import org.letmecode.autoinsurance.R
import org.letmecode.autoinsurance.base.BaseActivity

class AuthActivity : BaseActivity() {

    private lateinit var navController: NavController

    override fun contentResource(): Int {
        return R.layout.activity_auth
    }

    override fun setupView() {
        navController = Navigation.findNavController(this, R.id.navHostFragmentAuth)

    }

}