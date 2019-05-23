package org.letmecode.autoinsurance.ui.newpolicy

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.fragment_new_policy.*
import kotlinx.android.synthetic.main.layout_auto_diagnostic_card.*
import kotlinx.android.synthetic.main.layout_auto_information.*
import kotlinx.android.synthetic.main.layout_confirm_application_for_purchase.*
import kotlinx.android.synthetic.main.layout_confirmation_auto_information.*
import kotlinx.android.synthetic.main.layout_confirmation_personal_information.*
import kotlinx.android.synthetic.main.layout_confirmation_policy_information.*
import kotlinx.android.synthetic.main.layout_personal_driver_license.*
import kotlinx.android.synthetic.main.layout_personal_information.*
import kotlinx.android.synthetic.main.layout_personal_location.*
import kotlinx.android.synthetic.main.layout_policy_information.*
import org.letmecode.autoinsurance.R
import org.letmecode.autoinsurance.base.BaseFragment
import org.letmecode.autoinsurance.data.Policy
import java.text.SimpleDateFormat
import java.util.*


class NewPolicyFragment : BaseFragment() {

    private lateinit var navController: NavController
    private lateinit var viewModel: NewPolicyViewModel

    override fun contentResource(): Int {
        return R.layout.fragment_new_policy
    }

    override fun setupFirebaseAuth(): FirebaseAuth? {
        return FirebaseAuth.getInstance()
    }

    override fun setupDatabase(): DatabaseReference? {
        return firebaseDatabase.reference
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel = getViewModel(this, NewPolicyViewModel::class.java)

        viewModel.newPolicyResponse.observe(this, observerNewPolicyResponse())
        viewModel.errorLoading.observe(this, observerErrorLoading())
        viewModel.progressLoading.observe(this, observerProgressLoading())

    }

    override fun setupView() {

        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragmentUser)

        nextPersonalInformation.setOnClickListener {
            if (validatePersonalInformation()) {
                newPolicyFlipper.showNext()
            } else {
                showToast("Пожалуйста, заполните все поля!")
            }
        }

        nextPersonalLocation.setOnClickListener {
            if (validatePersonalLocation()) {
                newPolicyFlipper.showNext()
                setupConfirmationPersonalInformation()
            } else {
                showToast("Пожалуйста, заполните обязательные поля!")
            }
        }

        nextConfirmationPersonalInformation.setOnClickListener {
            newPolicyFlipper.showNext()
        }

        nextPersonalDriverLicense.setOnClickListener {
            if (validatePersonalDriverLicense()) {
                newPolicyFlipper.showNext()
            } else {
                showToast("Пожалуйста, заполните все поля!")
            }
        }

        nextAutoInformation.setOnClickListener {
            if (validateAutoInformation()) {
                newPolicyFlipper.showNext()
            } else {
                showToast("Пожалуйста, заполните обязательные поля!")
            }
        }

        nextAutoDiagnosticCard.setOnClickListener {
            newPolicyFlipper.showNext()
            setupConfirmationAutoInformation()
        }

        nextConfirmationAutoInformation.setOnClickListener {
            newPolicyFlipper.showNext()
        }

        nextPolicyInformation.setOnClickListener {
            if (validatePolicyInformation()) {
                newPolicyFlipper.showNext()
                setupConfirmationPolicyInformation()
            } else {
                showToast("Пожалуйста, заполните дату начала действия полиса!")
            }
        }

        nextConfirmationPolicyInformation.setOnClickListener {
            newPolicyFlipper.showNext()
        }

        doneConfirmAppForPurchase.setOnClickListener {
            if (validateConfirmAppForPurchase()) {
                val policy = createPolicyObject()
                viewModel.sendNewPolice(databaseReference, policy, policy.policyID, firebaseAuth?.currentUser?.uid!!)
            } else {
                showToast("Извините, вы не до конца заполнили анкету!")
            }
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Snackbar.LENGTH_LONG).show()
    }

    private fun validatePersonalInformation(): Boolean {
        return editTextSurname.text.isNotEmpty() &&
                editTextFirstName.text.isNotEmpty() &&
                editTextPatronymic.text.isNotEmpty() &&
                editTextDateOfBirth.text.isNotEmpty() &&
                (radioButtonMan.isChecked || radioButtonWoman.isChecked) &&
                editTextDocumentSeries.text.isNotEmpty() &&
                editTextDocumentNumber.text.isNotEmpty() &&
                editTextDateOfIssue.text.isNotEmpty() &&
                editTextDocumentIssuedBy.text.isNotEmpty()
    }

    private fun validatePersonalLocation(): Boolean {
        return editTextCity.text.isNotEmpty() &&
                editTextStreet.text.isNotEmpty() &&
                editTextHomeNumber.text.isNotEmpty()
    }

    private fun validatePersonalDriverLicense(): Boolean {
        return editTextAutoDocumentSeries.text.isNotEmpty() &&
                editTextAutoDocumentNumber.text.isNotEmpty() &&
                editTextAutoDocumentDateOfIssue.text.isNotEmpty()
    }

    private fun validateAutoInformation(): Boolean {
        return editTextAutoBrand.text.isNotEmpty() &&
                editTextAutoModel.text.isNotEmpty() &&
                editTextAutoIDNumber.text.isNotEmpty() &&
                editTextAutoPower.text.isNotEmpty()
    }

    private fun validatePolicyInformation(): Boolean {
        return editTextDateOfCommencementOfPolicy.text.isNotEmpty()
    }

    private fun validateConfirmAppForPurchase(): Boolean {
        return validatePersonalInformation() &&
                validatePersonalLocation() &&
                validatePersonalDriverLicense() &&
                validateAutoInformation() &&
                validatePolicyInformation() &&
                checkboxConfirmCorrectData.isChecked &&
                checkboxConfirmRule.isChecked
    }

    @SuppressLint("SetTextI18n")
    private fun setupConfirmationPersonalInformation() {
        textViewPersonalDocument.text = spinnerDocumentType.selectedItem.toString()
        textViewPersonalFullName.text = "${editTextSurname.text} ${editTextFirstName.text} ${editTextPatronymic.text}"
        textViewDateOfBirth.text = editTextDateOfBirth.text
        if (radioButtonMan.isChecked) {
            textViewGenderOfPerson.text = "Мужской"
        } else if (radioButtonWoman.isChecked) {
            textViewGenderOfPerson.text = "Женский"
        }
        textViewDocumentSeriesAndNumber.text = "${editTextDocumentSeries.text} ${editTextDocumentNumber.text}"
        textViewDocumentDateOfIssue.text = editTextDateOfIssue.text
        textViewDocumentIssueBy.text = editTextDocumentIssuedBy.text

        textViewPlaceOfResidence.text = "${editTextCity.text}, " +
                "ул. ${editTextStreet.text}, " +
                "д. ${editTextHomeNumber.text}, " +
                "к. ${editTextHousingNumber.text}, " +
                "с. ${editTextBuildingNumber.text}, " +
                "кв. ${editTextApartmentNumber.text}"
    }

    @SuppressLint("SetTextI18n")
    private fun setupConfirmationAutoInformation() {
        textViewAutoDocumentType.text = spinnerAutoDocumentType.selectedItem.toString()
        textViewAutoDocumentSeriesAndNumber.text = "${editTextAutoDocumentSeries.text} ${editTextAutoDocumentNumber.text}"
        textViewAutoDocumentDateOfIssue.text = editTextAutoDocumentDateOfIssue.text

        textViewAutoBrandAndModel.text = "${editTextAutoBrand.text} - ${editTextAutoModel.text}"
        textViewAutoYearOfIssue.text = spinnerYearOfIssue.selectedItem.toString()
        textViewAutoRegNumber.text = if (editTextAutoRegNumber.text.isNotEmpty()) {
            editTextAutoRegNumber.text
        } else {
            "Не указан"
        }
        textViewAutoIDNumberType.text = spinnerAutoIDType.selectedItem.toString()
        textViewAutoIDNumber.text = editTextAutoIDNumber.text
        textViewAutoBrandAndModelInVP.text = "${editTextAutoBrand.text} - ${editTextAutoModel.text}"
        textViewAutoPurposeOfUsing.text = spinnerAutoPurposeOfUsing.selectedItem.toString()
        textViewAutoPower.text = editTextAutoPower.text

        textViewNumberOfDiagnosticCard.text = if (editTextNumberOfDiagnosticCard.text.isNotEmpty()) {
            editTextNumberOfDiagnosticCard.text
        } else {
            "Не указан"
        }
        textViewDateDiagnosticCardOfIssue.text = if (editTextDateDiagnosticCardOfIssue.text.isNotEmpty()) {
            editTextDateDiagnosticCardOfIssue.text
        } else {
            "Не указан"
        }
        textViewDateDiagnosticCardValidUntil.text = if (editTextDateDiagnosticCardValidUntil.text.isNotEmpty()) {
            editTextDateDiagnosticCardValidUntil.text
        } else {
            "Не указан"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupConfirmationPolicyInformation() {
        textViewNewPolicyDateOfIssue.text = "${editTextDateOfCommencementOfPolicy.text} + 1 год"
    }

    private fun createPolicyObject(): Policy {
        val date = Calendar.getInstance().time
        val simpleDateFormat = SimpleDateFormat("dd-MMM-yyyy")
        val gender = if (radioButtonMan.isChecked) {
            "Мужчина"
        } else {
            "Женщина"
        }
        val placeOfResidence = "${editTextCity.text}, " +
                "ул. ${editTextStreet.text}, " +
                "д. ${editTextHomeNumber.text}, " +
                "к. ${editTextHousingNumber.text}, " +
                "с. ${editTextBuildingNumber.text}, " +
                "кв. ${editTextApartmentNumber.text}"
        val autoRegNumber = if (editTextAutoRegNumber.text.isNotEmpty()) {
            editTextAutoRegNumber.text
        } else {
            "Не указан"
        }
        val numberOfDiagnosticCard = if (editTextNumberOfDiagnosticCard.text.isNotEmpty()) {
            editTextNumberOfDiagnosticCard.text
        } else {
            "Не указан"
        }
        val dateDiagnosticCardOfIssue = if (editTextDateDiagnosticCardOfIssue.text.isNotEmpty()) {
            editTextDateDiagnosticCardOfIssue.text
        } else {
            "Не указан"
        }
        val dateDiagnosticCardValidUntil = if (editTextDateDiagnosticCardValidUntil.text.isNotEmpty()) {
            editTextDateDiagnosticCardValidUntil.text
        } else {
            "Не указан"
        }
        return Policy(UUID.randomUUID().toString(),
                editTextSurname.text.toString(),
                editTextFirstName.text.toString(),
                editTextPatronymic.text.toString(),
                editTextDateOfBirth.text.toString(),
                gender,
                spinnerDocumentType.selectedItem.toString(),
                editTextDocumentSeries.text.toString(),
                editTextDocumentNumber.text.toString(),
                editTextDateOfIssue.text.toString(),
                editTextDocumentIssuedBy.text.toString(),
                placeOfResidence,
                spinnerAutoDocumentType.selectedItem.toString(),
                editTextAutoDocumentSeries.text.toString(),
                editTextAutoDocumentNumber.text.toString(),
                editTextAutoBrand.text.toString(),
                editTextAutoModel.text.toString(),
                spinnerYearOfIssue.selectedItem.toString(),
                autoRegNumber.toString(),
                spinnerAutoIDType.selectedItem.toString(),
                editTextAutoIDNumber.text.toString(),
                spinnerAutoPurposeOfUsing.selectedItem.toString(),
                editTextAutoPower.text.toString(),
                numberOfDiagnosticCard.toString(),
                dateDiagnosticCardOfIssue.toString(),
                dateDiagnosticCardValidUntil.toString(),
                "false",
                simpleDateFormat.format(date))
    }

    private fun clearAllFields() {
        editTextSurname.setText("")
        editTextFirstName.setText("")
        editTextPatronymic.setText("")
        editTextDateOfBirth.setText("")
        editTextDocumentSeries.setText("")
        editTextDocumentNumber.setText("")
        editTextDateOfIssue.setText("")
        editTextDocumentIssuedBy.setText("")
        editTextAutoDocumentSeries.setText("")
        editTextAutoDocumentNumber.setText("")
        editTextAutoBrand.setText("")
        editTextAutoModel.setText("")
        editTextAutoIDNumber.setText("")
        editTextAutoPower.setText("")
        editTextCity.setText("")
        editTextStreet.setText("")
        editTextHomeNumber.setText("")
        editTextHousingNumber.setText("")
        editTextBuildingNumber.setText("")
        editTextApartmentNumber.setText("")
        editTextAutoRegNumber.setText("")
        editTextNumberOfDiagnosticCard.setText("")
        editTextDateDiagnosticCardOfIssue.setText("")
        editTextDateDiagnosticCardValidUntil.setText("")
    }

    private fun observerNewPolicyResponse(): Observer<in Policy?> {
        return Observer {
            clearAllFields()
            navController.navigate(R.id.action_newPolicyFragment_to_successFragment)
        }
    }

}