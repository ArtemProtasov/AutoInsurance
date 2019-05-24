package org.letmecode.autoinsurance.ui.managerscreen

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_user.*
import org.letmecode.autoinsurance.R
import org.letmecode.autoinsurance.base.BaseActivity
import org.letmecode.autoinsurance.type.UserType

class ManagerActivity : BaseActivity() {

    private lateinit var navController: NavController

    override fun contentResource(): Int {
        return R.layout.activity_manager
    }

    override fun setupView() {
        navController = Navigation.findNavController(this, R.id.navHostFragmentManager)

        val bundle = Bundle()
        bundle.putString("userType", UserType.MANAGER.userType)

        bottomNavView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ordersFragment -> {
                    navController.navigate(R.id.ordersFragment2, bundle)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.newPolicyFragment -> {
                    navController.navigate(R.id.newPolicyFragment2, bundle)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.settingsFragment -> {
                    navController.navigate(R.id.settingsFragment2, bundle)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }


}
