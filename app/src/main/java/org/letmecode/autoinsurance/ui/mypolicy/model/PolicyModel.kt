package org.letmecode.autoinsurance.ui.mypolicy.model

import android.annotation.SuppressLint
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import kotlinx.android.synthetic.main.epoxy_my_policy.view.*
import org.letmecode.autoinsurance.R
import org.letmecode.autoinsurance.data.Policy
import org.letmecode.autoinsurance.ui.mypolicy.MyPolicyController

@EpoxyModelClass(layout = R.layout.epoxy_my_policy)
abstract class PolicyModel : EpoxyModelWithHolder<Holder>() {

    @EpoxyAttribute
    var policy: Policy? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var myPolicyListener: MyPolicyController.MyPolicyListener? = null

    @SuppressLint("SetTextI18n")
    override fun bind(holder: Holder) {
        super.bind(holder)

        when {
            policy?.approval == "true" -> holder.imageViewOrderApproved.setImageDrawable(holder.imageViewOrderApproved.context.getDrawable(R.drawable.ic_check_green))
            policy?.approval == "false" -> holder.imageViewOrderApproved.setImageDrawable(holder.imageViewOrderApproved.context.getDrawable(R.drawable.ic_check_red))
            policy?.approval == "cancel" -> holder.imageViewOrderApproved.setImageDrawable(holder.imageViewOrderApproved.context.getDrawable(R.drawable.ic_cancel))
        }

        holder.textViewUserName.text = "${policy?.surname} ${policy?.firstName} ${policy?.patronymic}"
        holder.textViewAutoBrandAndModel.text = "${policy?.autoBrand} ${policy?.autoModel}"
        holder.textViewAutoYearOfIssue.text = policy?.autoYearOfIssue
        holder.textViewAutoRegNumber.text = policy?.autoRegNumber
        holder.textViewAutoPurposeOfUsing.text = policy?.autoPurposeOfUsing
        holder.textViewAutoPower.text = policy?.autoPower
        holder.textViewOfficeAddress.text = if (policy?.officeAddress?.isNotEmpty()!!) {
            policy?.officeAddress
        } else {
            "На рассмотрении"
        }
        holder.textViewPrice.text = if (policy?.price?.isNotEmpty()!!) {
            "${policy?.price} руб"
        } else {
            "На рассмотрении..."
        }

        holder.buttonShowOrder.setOnClickListener {
            myPolicyListener?.onClickPolicy(policy!!)
        }
    }

}

class Holder : EpoxyHolder() {

    lateinit var imageViewOrderApproved: ImageView

    lateinit var textViewUserName: TextView
    lateinit var textViewAutoBrandAndModel: TextView
    lateinit var textViewAutoYearOfIssue: TextView
    lateinit var textViewAutoRegNumber: TextView
    lateinit var textViewAutoPurposeOfUsing: TextView
    lateinit var textViewAutoPower: TextView
    lateinit var textViewOfficeAddress: TextView
    lateinit var textViewPrice: TextView

    lateinit var buttonShowOrder: Button

    override fun bindView(itemView: View) {
        imageViewOrderApproved = itemView.imageViewOrderApproved

        textViewUserName = itemView.textViewUserName
        textViewAutoBrandAndModel = itemView.textViewAutoBrandAndModel
        textViewAutoYearOfIssue = itemView.textViewAutoYearOfIssue
        textViewAutoRegNumber = itemView.textViewAutoRegNumber
        textViewAutoPurposeOfUsing = itemView.textViewAutoPurposeOfUsing
        textViewAutoPower = itemView.textViewAutoPower
        textViewOfficeAddress = itemView.textViewOfficeAddress
        textViewPrice = itemView.textViewPrice

        buttonShowOrder = itemView.buttonShowOrder
    }

}