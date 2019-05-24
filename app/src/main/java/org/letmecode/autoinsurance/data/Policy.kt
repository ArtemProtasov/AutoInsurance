package org.letmecode.autoinsurance.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Policy(
        @SerializedName("policyID") val policyID: String = "",
        @SerializedName("surname") val surname: String = "",
        @SerializedName("firstName") val firstName: String = "",
        @SerializedName("patronymic") val patronymic: String = "",
        @SerializedName("dateOfBirth") val dateOfBirth: String = "",
        @SerializedName("gender") val gender: String = "",
        @SerializedName("documentType") val documentType: String = "",
        @SerializedName("documentSeries") val documentSeries: String = "",
        @SerializedName("documentNumber") val documentNumber: String = "",
        @SerializedName("documentDateOfIssue") val documentDateOfIssue: String = "",
        @SerializedName("documentIssueBy") val documentIssueBy: String = "",
        @SerializedName("placeOfResidence") val placeOfResidence: String = "",
        @SerializedName("autoDocumentType") val autoDocumentType: String = "",
        @SerializedName("autoDocumentSeries") val autoDocumentSeries: String = "",
        @SerializedName("autoDocumentNumber") val autoDocumentNumber: String = "",
        @SerializedName("autoBrand") val autoBrand: String = "",
        @SerializedName("autoModel") val autoModel: String = "",
        @SerializedName("autoYearOfIssue") val autoYearOfIssue: String = "",
        @SerializedName("autoRegNumber") val autoRegNumber: String = "",
        @SerializedName("autoIDNumberType") val autoIDNumberType: String = "",
        @SerializedName("autoIDNumber") val autoIDNumber: String = "",
        @SerializedName("autoPurposeOfUsing") val autoPurposeOfUsing: String = "",
        @SerializedName("autoPower") val autoPower: String = "",
        @SerializedName("autoNumberOfDiagnosticCard") val autoNumberOfDiagnosticCard: String = "",
        @SerializedName("autoDateDiagnosticCardOfIssue") val autoDateDiagnosticCardOfIssue: String = "",
        @SerializedName("autoDateDiagnosticCardValidUntil") val autoDateDiagnosticCardValidUntil: String = "",
        @SerializedName("approval") val approval: String = "",
        @SerializedName("date") val date: String = "",
        @SerializedName("userUID") val userUID: String = "",
        @SerializedName("price") val price: String = "",
        @SerializedName("officeAddress") val officeAddress: String = ""
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(policyID)
        writeString(surname)
        writeString(firstName)
        writeString(patronymic)
        writeString(dateOfBirth)
        writeString(gender)
        writeString(documentType)
        writeString(documentSeries)
        writeString(documentNumber)
        writeString(documentDateOfIssue)
        writeString(documentIssueBy)
        writeString(placeOfResidence)
        writeString(autoDocumentType)
        writeString(autoDocumentSeries)
        writeString(autoDocumentNumber)
        writeString(autoBrand)
        writeString(autoModel)
        writeString(autoYearOfIssue)
        writeString(autoRegNumber)
        writeString(autoIDNumberType)
        writeString(autoIDNumber)
        writeString(autoPurposeOfUsing)
        writeString(autoPower)
        writeString(autoNumberOfDiagnosticCard)
        writeString(autoDateDiagnosticCardOfIssue)
        writeString(autoDateDiagnosticCardValidUntil)
        writeString(approval)
        writeString(date)
        writeString(userUID)
        writeString(price)
        writeString(officeAddress)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Policy> = object : Parcelable.Creator<Policy> {
            override fun createFromParcel(source: Parcel): Policy = Policy(source)
            override fun newArray(size: Int): Array<Policy?> = arrayOfNulls(size)
        }
    }
}