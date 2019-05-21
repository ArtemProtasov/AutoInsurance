package org.letmecode.autoinsurance.ui.userscreen

import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_user.*
import org.letmecode.autoinsurance.R
import org.letmecode.autoinsurance.base.BaseActivity

class UserActivity : BaseActivity() {

    private lateinit var navController: NavController

    override fun contentResource(): Int {
        return R.layout.activity_user
    }

    override fun setupView() {

        navController = Navigation.findNavController(this, R.id.navHostFragmentUser)

        bottomNavView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.myPolicy -> {
                    navController.navigate(R.id.myPolicyFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.newPolicy -> {
                    navController.navigate(R.id.newPolicyFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.appSettings -> {
                    navController.navigate(R.id.settingsFragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }

}