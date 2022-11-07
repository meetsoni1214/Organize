package com.example.organize.adapter

import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentManager
import com.example.organize.BankFinalFragment
import com.example.organize.R
import com.example.organize.data.EmailAccount
import com.example.organize.model.HasEmail
import com.example.organize.model.SelectedStatus


class BindingAdapters {

}
@BindingAdapter("atmCardCheckedButton")
fun atmCardView(radioGroup: RadioGroup, status: SelectedStatus) {

    if(status == SelectedStatus.NO) {
            radioGroup.check(R.id.atm_card_option_no)
        }else {
            radioGroup.check(R.id.atm_card_option_yes)
        }
}

@BindingAdapter("upiCheckedButton")
fun upiView(radioGroup: RadioGroup, status: SelectedStatus) {

    if(status == SelectedStatus.NO) {
        radioGroup.check(R.id.upi_option_no)
    }else {
        radioGroup.check(R.id.upi_option_yes)
    }
}

@BindingAdapter("mAppCheckedButton")
fun mAppView(radioGroup: RadioGroup, status: SelectedStatus) {

    if(status == SelectedStatus.NO) {
        radioGroup.check(R.id.banking_app_option_no)
    }else {
        radioGroup.check(R.id.banking_app_option_yes)
    }
}
