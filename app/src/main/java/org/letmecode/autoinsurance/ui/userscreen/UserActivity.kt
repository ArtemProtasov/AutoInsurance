package org.letmecode.autoinsurance.ui.userscreen

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_user.*
import org.letmecode.autoinsurance.R
import org.letmecode.autoinsurance.base.BaseActivity
import org.letmecode.autoinsurance.type.UserType

class UserActivity : BaseActivity() {

    private lateinit var navController: NavController

    override fun contentResource(): Int {
        return R.layout.activity_user
    }

    override fun setupView() {

        navController = Navigation.findNavController(this, R.id.navHostFragmentUser)

        val bundle = Bundle()
        bundle.putString("userType", UserType.USER.userType)

        bottomNavView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.myPolicyFragment -> {
                    navController.navigate(R.id.myPolicyFragment, bundle)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.newPolicyFragment2 -> {
                    navController.navigate(R.id.newPolicyFragment, bundle)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.settingsFragment2 -> {
                    navController.navigate(R.id.settingsFragment, bundle)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })

    }

}