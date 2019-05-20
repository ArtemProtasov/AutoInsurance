package org.letmecode.autoinsurance.ui.auth.signup

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.letmecode.autoinsurance.R
import org.letmecode.autoinsurance.base.BaseFragment
import org.letmecode.autoinsurance.type.UserType

/**
 * Created by Artem Protasov (zippe.inc@gmail.com) on [14-05-2019].
 */
class SignUpFragment : BaseFragment() {

    private lateinit var navController: NavController
    private lateinit var viewModel: SignUpViewModel

    override fun contentResource(): Int {
        return R.layout.fragment_sign_up
    }

    override fun setupFirebaseAuth(): FirebaseAuth? {
        return FirebaseAuth.getInstance()
    }

    override fun setupDatabase(): DatabaseReference? {
        return firebaseDatabase.reference
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel = getViewModel(this, SignUpViewModel::class.java)

        viewModel.addUserType.observe(this, observerAddUserType())
        viewModel.errorLoading.observe(this, observerErrorLoading())
    }

    override fun setupView() {
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragmentAuth)

        validateInput()

        signUpButton.setOnClickListener {
            firebaseAuth?.createUserWithEmailAndPassword(inputLogin.text.toString(), inputPassword.text.toString())
                    ?.addOnCompleteListener(
                            requireActivity()
                    ) {
                        if (it.isSuccessful) {
                            viewModel.saveUserType(databaseReference, spinnerAccountType.selectedItem.toString(), firebaseAuth?.currentUser?.uid!!)
                        } else {
                            Snackbar.make(rootView, it.exception?.localizedMessage.toString(), Snackbar.LENGTH_LONG).show()
                        }
                    }
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

        inputRepeatPassword.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                validateInput()
            }

        })

        spinnerAccountType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                validateInput()
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                validateInput()
            }

        }

    }

    fun validateInput() {
        signUpButton.isEnabled =
                inputLogin.text.isNotEmpty() &&
                        inputPassword.text.isNotEmpty() &&
                        inputPassword.text.toString() == inputRepeatPassword.text.toString() &&
                        (spinnerAccountType.selectedItem == "Менеджер" || spinnerAccountType.selectedItem == "Пользователь")
    }

    private fun observerAddUserType(): Observer<in Boolean?> {
        return Observer {
            if (it!!) {
                navController.navigate(R.id.action_signUpFragment_to_userActivity)
            }
        }
    }

}