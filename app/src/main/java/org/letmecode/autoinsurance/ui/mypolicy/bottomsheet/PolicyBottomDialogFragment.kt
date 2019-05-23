package org.letmecode.autoinsurance.ui.mypolicy.bottomsheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.layout_policy_bottom_sheet.*
import org.letmecode.autoinsurance.R
import org.letmecode.autoinsurance.data.Policy

class PolicyBottomDialogFragment : BottomSheetDialogFragment() {

    private var policy: Policy? = null

    companion object {
        private const val ARG_POLICY = "arg_policy"

        fun newInstance(policy: Policy) = PolicyBottomDialogFragment().apply {
            arguments = Bundle(2).apply {
                putParcelable(ARG_POLICY, policy)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_policy_bottom_sheet, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    }

}