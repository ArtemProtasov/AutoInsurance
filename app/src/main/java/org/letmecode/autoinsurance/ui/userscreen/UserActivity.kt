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
                    println(1111)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.newPolicy -> {
                    println(2222)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.appSettings -> {
                    println(3333)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }

}