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

        if (policy?.approval == "true") {
            holder.imageViewOrderApproved.setImageDrawable(holder.imageViewOrderApproved.context.getDrawable(R.drawable.ic_check_green))
        } else {
            holder.imageViewOrderApproved.setImageDrawable(holder.imageViewOrderApproved.context.getDrawable(R.drawable.ic_check_red))
        }

        holder.textViewUserName.text = "${policy?.surname} ${policy?.firstName} ${policy?.patronymic}"
        holder.textViewAutoBrandAndModel.text = "${policy?.autoBrand} ${policy?.autoModel}"
        holder.textViewAutoYearOfIssue.text = policy?.autoYearOfIssue
        holder.textViewAutoRegNumber.text = policy?.autoRegNumber
        holder.textViewAutoPurposeOfUsing.text = policy?.autoPurposeOfUsing
        holder.textViewAutoPower.text = policy?.autoPower


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

    lateinit var buttonShowOrder: Button

    override fun bindView(itemView: View) {
        imageViewOrderApproved = itemView.imageViewOrderApproved

        textViewUserName = itemView.textViewUserName
        textViewAutoBrandAndModel = itemView.textViewAutoBrandAndModel
        textViewAutoYearOfIssue = itemView.textViewAutoYearOfIssue
        textViewAutoRegNumber = itemView.textViewAutoRegNumber
        textViewAutoPurposeOfUsing = itemView.textViewAutoPurposeOfUsing
        textViewAutoPower = itemView.textViewAutoPower

        buttonShowOrder = itemView.buttonShowOrder
    }

}