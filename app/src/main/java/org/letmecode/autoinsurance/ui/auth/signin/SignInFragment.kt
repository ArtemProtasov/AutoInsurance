package org.letmecode.autoinsurance.ui.auth.signin

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.progress_layout.*
import org.letmecode.autoinsurance.R
import org.letmecode.autoinsurance.base.BaseFragment
import org.letmecode.autoinsurance.type.UserType

/**
 * Created by Artem Protasov (zippe.inc@gmail.com) on [14-05-2019].
 */
class SignInFragment : BaseFragment() {

    private lateinit var navController: NavController
    private lateinit var viewModel: SignInViewModel

    override fun contentResource(): Int {
        return R.layout.fragment_sign_in
    }

    override fun setupFirebaseAuth(): FirebaseAuth? {
        return FirebaseAuth.getInstance()
    }

    override fun setupDatabase(): DatabaseReference? {
        return firebaseDatabase.reference
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel = getViewModel(this, SignInViewModel::class.java)

        viewModel.errorLoading.observe(this, observerErrorLoading())
        viewModel.progressLoading.observe(this, observerProgressLoading())
        viewModel.userTypeResponse.observe(this, observerUserType())
    }

    override fun getProgress(): View? {
        return progressView
    }

    override fun getViewGroup(): ViewGroup? {
        return rootView
    }

    override fun setupView() {
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragmentAuth)

        validateInput()

        logInButton.setOnClickListener {
            firebaseAuth?.signInWithEmailAndPassword(inputLogin.text.toString(), inputPassword.text.toString())
                    ?.addOnCompleteListener(
                            requireActivity()
                    ) {
                        if (it.isSuccessful) {
                            viewModel.requestUserType(databaseReference, firebaseAuth?.currentUser?.uid!!, "userType")
                        } else {
                            inputPassword.text.clear()
                            Snackbar.make(rootView, it.exception?.localizedMessage.toString(), Snackbar.LENGTH_LONG).show()
                        }
                    }
        }

        signUpButton.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        inputLogin.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                validateInput()
            }

        })

        inputPassword.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                validateInput()
            }

        })
    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth?.currentUser != null) {
            viewModel.requestUserType(databaseReference, firebaseAuth?.currentUser?.uid!!, "userType")
        } else {
            getProgress()?.visibility = View.GONE
        }
    }

    private fun observerUserType(): Observer<in String?> {
        return Observer {
            firebaseAuth?.updateCurrentUser(firebaseAuth?.currentUser!!)
            if(it == UserType.USER.userType) {
                navController.navigate(R.id.action_signInFragment_to_userActivity)
            } else {
                navController.navigate(R.id.action_signInFragment_to_managerActivity)
            }
        }
    }

    fun validateInput() {
        logInButton.isEnabled = inputPassword.text.isNotEmpty() && inputLogin.text.isNotEmpty()
    }

}