package org.letmecode.autoinsurance.ui.auth.signin

import android.text.Editable
import android.text.TextWatcher
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.letmecode.autoinsurance.R
import org.letmecode.autoinsurance.base.BaseFragment

/**
 * Created by Artem Protasov (zippe.inc@gmail.com) on [14-05-2019].
 */
class SignInFragment : BaseFragment() {

    private lateinit var navController: NavController

    override fun contentResource(): Int {
        return R.layout.fragment_sign_in
    }

    override fun setupFirebaseAuth(): FirebaseAuth? {
        return FirebaseAuth.getInstance()
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
                        navController.navigate(R.id.action_signInFragment_to_userActivity)
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
            firebaseAuth?.updateCurrentUser(firebaseAuth?.currentUser!!)
            navController.navigate(R.id.action_signInFragment_to_userActivity)
        }
    }

    fun validateInput() {
        logInButton.isEnabled = inputPassword.text.isNotEmpty() && inputLogin.text.isNotEmpty()
    }

}