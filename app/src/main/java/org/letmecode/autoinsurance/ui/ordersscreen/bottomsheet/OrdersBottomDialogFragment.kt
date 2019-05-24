package org.letmecode.autoinsurance.ui.ordersscreen.bottomsheet

import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.layout_orders_bottom_sheet.*
import org.letmecode.autoinsurance.R
import org.letmecode.autoinsurance.base.BaseBottomSheetDialogFragment
import org.letmecode.autoinsurance.data.Policy
import org.letmecode.autoinsurance.type.PolicyFields

class OrdersBottomDialogFragment : BaseBottomSheetDialogFragment() {

    private var policy: Policy? = null
    private lateinit var viewModel: OrdersBottomDialogViewModel

    companion object {
        private const val ARG_POLICY = "arg_policy"

        fun newInstance(policy: Policy) = OrdersBottomDialogFragment().apply {
            arguments = Bundle(2).apply {
                putParcelable(ARG_POLICY, policy)
            }
        }
    }

    override fun contentResource(): Int {
        return R.layout.layout_orders_bottom_sheet
    }

    override fun setupFirebaseAuth(): FirebaseAuth? {
        return FirebaseAuth.getInstance()
    }

    override fun setupDatabase(): DatabaseReference? {
        return firebaseDatabase.reference
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel = getViewModel(this, OrdersBottomDialogViewModel::class.java)

        viewModel.errorLoading.observe(this, observerErrorLoading())
    }

    override fun setupView() {
        policy = arguments?.getParcelable(ARG_POLICY)

        textViewBottomSheetTitle.text = "Полис E-ОСАГО\nдля ${policy?.autoBrand} ${policy?.autoModel}"

        textViewPersonalDocument.text = policy?.documentType
        textViewPersonalFullName.text = "${policy?.surname} ${policy?.firstName} ${policy?.patronymic}"
        textViewDateOfBirth.text = policy?.dateOfBirth
        textViewGenderOfPerson.text = policy?.gender
        textViewDocumentSeriesAndNumber.text = "${policy?.documentSeries} ${policy?.documentNumber}"
        textViewDocumentDateOfIssue.text = policy?.documentDateOfIssue
        textViewDocumentIssueBy.text = policy?.documentIssueBy

        textViewPlaceOfResidence.text = policy?.placeOfResidence

        textViewAutoDocumentType.text = policy?.autoDocumentType
        textViewAutoDocumentSeriesAndNumber.text = "${policy?.autoDocumentSeries} ${policy?.autoDocumentNumber}"
        textViewAutoDocumentDateOfIssue.text = policy?.documentDateOfIssue

        textViewAutoBrandAndModel.text = "${policy?.autoBrand} ${policy?.autoModel}"
        textViewAutoYearOfIssue.text = policy?.autoYearOfIssue
        textViewAutoRegNumber.text = policy?.autoRegNumber
        textViewAutoIDNumberType.text = policy?.autoIDNumberType
        textViewAutoIDNumber.text = policy?.autoIDNumber
        textViewAutoBrandAndModelInVP.text = "${policy?.autoBrand} ${policy?.autoModel}"
        textViewAutoPurposeOfUsing.text = policy?.autoPurposeOfUsing
        textViewAutoPower.text = policy?.autoPower

        textViewNumberOfDiagnosticCard.text = policy?.autoNumberOfDiagnosticCard
        textViewDateDiagnosticCardOfIssue.text = policy?.autoDateDiagnosticCardOfIssue
        textViewDateDiagnosticCardValidUntil.text = policy?.autoDateDiagnosticCardValidUntil

        textViewNewPolicyDateOfIssue.text = "1 год"

        buttonReject.setOnClickListener {
            policy?.let {
                viewModel.requestDataChange(databaseReference, it.userUID, it.policyID, "policy", PolicyFields.approval.field, "cancel", PolicyFields.price.field, "0", PolicyFields.officeAddress.field, "N/A", this)
            }
        }

        buttonAccept.setOnClickListener {
            policy?.let {
                if (editTextPrice.text.isNotEmpty()) {
                    viewModel.requestDataChange(
                            databaseReference,
                            it.userUID,
                            it.policyID,
                            "policy",
                            PolicyFields.approval.field,
                            "true",
                            PolicyFields.price.field,
                            editTextPrice.text.toString(),
                            PolicyFields.officeAddress.field,
                            spinnerOfficeAddress.selectedItem.toString(),
                            this)
                } else {
                    editTextPrice.error = "Пожалуйста, введите сумму для оплаты"
                }
            }
        }
    }

}