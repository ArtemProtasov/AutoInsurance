package org.letmecode.autoinsurance.ui.newpolicy.successscreen

import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_success.*
import org.letmecode.autoinsurance.R
import org.letmecode.autoinsurance.base.BaseFragment

class SuccessFragment : BaseFragment() {

    private lateinit var navController: NavController

    override fun contentResource(): Int {
        return R.layout.fragment_success
    }

    override fun setupView() {

        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragmentUser)

        buttonContinue.setOnClickListener {
            navController.navigate(R.id.action_successFragment_to_newPolicyFragment)
        }
    }

}