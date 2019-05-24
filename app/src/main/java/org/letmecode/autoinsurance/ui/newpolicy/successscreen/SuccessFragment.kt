package org.letmecode.autoinsurance.ui.newpolicy.successscreen

import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_success.*
import org.letmecode.autoinsurance.R
import org.letmecode.autoinsurance.base.BaseFragment
import org.letmecode.autoinsurance.type.UserType

class SuccessFragment : BaseFragment() {

    private lateinit var navController: NavController

    override fun contentResource(): Int {
        return R.layout.fragment_success
    }

    override fun setupView() {

        arguments?.let {
            navController = if (it.getString("userType") == UserType.USER.userType) {
                Navigation.findNavController(requireActivity(), R.id.navHostFragmentUser)
            } else {
                Navigation.findNavController(requireActivity(), R.id.navHostFragmentManager)
            }
        }

        buttonContinue.setOnClickListener {
            arguments?.let {
                if (it.getString("userType") == UserType.USER.userType) {
                    navController.navigate(R.id.action_successFragment_to_newPolicyFragment2)
                } else {
                    navController.navigate(R.id.action_successFragment2_to_newPolicyFragment)
                }
            }
        }
    }

}