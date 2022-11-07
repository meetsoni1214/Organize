package com.example.organize


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.organize.databinding.FragmentBankTwoBinding
import com.example.organize.model.CategoryViewModel
import com.example.organize.model.SelectedStatus
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class BankTwoFragment : Fragment() {

    private val sharedViewModel: CategoryViewModel by activityViewModels()

    private var _binding: FragmentBankTwoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBankTwoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            fragmentBankTwo = this@BankTwoFragment
        }
        val expiryDate: TextInputLayout = binding.cardExpiryDateLayout
        val dateentered: TextInputEditText = binding.cardExpiryDate
        expiryDate.setEndIconOnClickListener {
            // Create new instance of DatePickerFragment
            val datePickerFragment = DatePickerFragment()
            val supportFragmentManager = requireActivity().supportFragmentManager

            // we have to implement setFragmentResultListener
            supportFragmentManager.setFragmentResultListener(
                "REQUEST_KEY",
                viewLifecycleOwner
            ) { resultKey, bundle ->
                if (resultKey == "REQUEST_KEY") {
                    val date = bundle.getString("SELECTED_DATE")
                    dateentered.setText(date)
                }
            }
            // show
            datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
        }

        val cardStatus: SelectedStatus? = sharedViewModel.atmCardStatus.value
        if (cardStatus == SelectedStatus.NO) {
            hideCardView()
        }

        val upiPinStatus: SelectedStatus? = sharedViewModel.upiStatus.value
        if (upiPinStatus == SelectedStatus.NO) {
            hideUPIView()
        }

        val mAppStatus: SelectedStatus? = sharedViewModel.mobileAppStatus.value
        if (mAppStatus == SelectedStatus.NO) {
            hideAppView()
        }
    }
    private fun hideCardView() {
        val cardHolderName: TextInputLayout = binding.cardHolderNameLayout
        val cardNumber: TextInputLayout = binding.cardNoLayout
        val expiryDate: TextInputLayout = binding.cardExpiryDateLayout
        val cvv: TextInputLayout = binding.cardCvvLayout
        val atmPin: TextInputLayout = binding.atmPinLayout
        cardHolderName.visibility = (View.GONE)
        cardNumber.visibility = (View.GONE)
        expiryDate.visibility = (View.GONE)
        cvv.visibility = (View.GONE)
        atmPin.visibility = (View.GONE)
        val constraintLayout: ConstraintLayout = binding.bankTwoLayout
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.connect(
            R.id.upi_option_question,
            ConstraintSet.START,
            R.id.atm_card_options,
            ConstraintSet.START,
            0
        )
        constraintSet.connect(
            R.id.upi_option_question,
            ConstraintSet.TOP,
            R.id.atm_card_options,
            ConstraintSet.BOTTOM,
            0
        )
        constraintSet.applyTo(constraintLayout)

    }

    private fun unhideCardView() {
        val cardHolderName: TextInputLayout = binding.cardHolderNameLayout
        val cardNumber: TextInputLayout = binding.cardNoLayout
        val expiryDate: TextInputLayout = binding.cardExpiryDateLayout
        val cvv: TextInputLayout = binding.cardCvvLayout
        val atmPin: TextInputLayout = binding.atmPinLayout
        cardHolderName.visibility = (View.VISIBLE)
        cardNumber.visibility = (View.VISIBLE)
        expiryDate.visibility = (View.VISIBLE)
        cvv.visibility = (View.VISIBLE)
        atmPin.visibility = (View.VISIBLE)
        val constraintLayout: ConstraintLayout = binding.bankTwoLayout
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.connect(
            R.id.upi_option_question,
            ConstraintSet.START,
            R.id.atm_pin_layout,
            ConstraintSet.START,
            0
        )
        constraintSet.connect(
            R.id.upi_option_question,
            ConstraintSet.TOP,
            R.id.atm_pin_layout,
            ConstraintSet.BOTTOM,
            18
        )
        constraintSet.applyTo(constraintLayout)
    }

    private fun hideUPIView() {
        val upiPIN: TextInputLayout = binding.upiPinLayout
        upiPIN.visibility = (View.GONE)
        val constraintLayout: ConstraintLayout = binding.bankTwoLayout
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.connect(
            R.id.mobile_banking_app_question,
            ConstraintSet.START,
            R.id.upi_option,
            ConstraintSet.START,
            0
        )
        constraintSet.connect(
            R.id.mobile_banking_app_question,
            ConstraintSet.TOP,
            R.id.upi_option,
            ConstraintSet.BOTTOM,
            0
        )
        constraintSet.applyTo(constraintLayout)
    }

    private fun unhideUPIView() {
        val upiPIN: TextInputLayout = binding.upiPinLayout
        upiPIN.visibility = (View.VISIBLE)
        val constraintLayout: ConstraintLayout = binding.bankTwoLayout
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.connect(
            R.id.mobile_banking_app_question,
            ConstraintSet.START,
            R.id.upi_pin_layout,
            ConstraintSet.START,
            0
        )
        constraintSet.connect(
            R.id.mobile_banking_app_question,
            ConstraintSet.TOP,
            R.id.upi_pin_layout,
            ConstraintSet.BOTTOM,
            18
        )
        constraintSet.applyTo(constraintLayout)
    }

    private fun hideAppView() {
        val appLogin: TextInputLayout = binding.bankingAppLoginLayout
        appLogin.visibility = (View.GONE)
        val appPassword: TextInputLayout = binding.bankingAppPasswordLayout
        appPassword.visibility = (View.GONE)
        val constraintLayout: ConstraintLayout = binding.bankTwoLayout
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.connect(
            R.id.submit_button_bank_two,
            ConstraintSet.TOP,
            R.id.banking_app_option,
            ConstraintSet.BOTTOM,
            0
        )
        constraintSet.applyTo(constraintLayout)
    }

    private fun unhideAppView() {
        val appLogin: TextInputLayout = binding.bankingAppLoginLayout
        appLogin.visibility = (View.VISIBLE)
        val appPassword: TextInputLayout = binding.bankingAppPasswordLayout
        appPassword.visibility = (View.VISIBLE)
        val constraintLayout: ConstraintLayout = binding.bankTwoLayout
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.connect(
            R.id.submit_button_bank_two,
            ConstraintSet.TOP,
            R.id.banking_app_password_layout,
            ConstraintSet.BOTTOM,
            16
        )
        constraintSet.applyTo(constraintLayout)
    }

    fun submitDetail() {
        val cName = binding.cardHolderName.editableText.toString()
        val cNo = binding.cardNo.editableText.toString()
        val cDate = binding.cardExpiryDate.editableText.toString()
        val cCvv = binding.cardCvv.editableText.toString()
        val cPin = binding.atmPin.editableText.toString()
        val uPin = binding.upiPin.editableText.toString()
        val mPin = binding.bankingAppLogin.editableText.toString()
        val tPin = binding.bankingAppPassword.editableText.toString()
        sharedViewModel.addExtraBankDetails(cName, cNo, cDate, cCvv, cPin, uPin, mPin, tPin)
        findNavController().navigate(R.id.action_bankTwoFragment_to_bankFinalFragment)
    }

    fun noOptionCardClick() {
        val noOptionCard: RadioButton = binding.atmCardOptionNo
        if (noOptionCard.isChecked) {
            hideCardView()
            sharedViewModel.noAtmSelected()
        }
    }
    fun yesOptionCardClick() {
        val yesOptionCard: RadioButton = binding.atmCardOptionYes
            if (yesOptionCard.isChecked) {
                unhideCardView()
                sharedViewModel.yesAtmSelected()
            }
    }

    fun noUpiOptionClick() {
        val noOptionUPI: RadioButton = binding.upiOptionNo
            if (noOptionUPI.isChecked) {
                hideUPIView()
                sharedViewModel.noUpiSelected()
            }
    }

    fun yesUpiOptionClick() {
        val yesOptionUPI: RadioButton = binding.upiOptionYes
            if (yesOptionUPI.isChecked) {
                unhideUPIView()
                sharedViewModel.yesUpiSelected()
            }
    }

    fun noBankingAppOptionClick() {
        val noOptionApp: RadioButton = binding.bankingAppOptionNo
            if (noOptionApp.isChecked) {
                hideAppView()
                sharedViewModel.noBankingAppSelected()
            }
    }

    fun yesBankingAppOptionClick() {
        val yesOptionApp: RadioButton = binding.bankingAppOptionYes
            if (yesOptionApp.isChecked) {
                unhideAppView()
                sharedViewModel.yesBankingAppSelected()
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
