package com.example.organize


import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.organize.data.BankAccount
import com.example.organize.databinding.FragmentBankOneBinding
import com.example.organize.model.BankViewModel
import com.example.organize.model.BankViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class BankOneFragment : Fragment() {
    private var _binding: FragmentBankOneBinding? = null
    private val binding get() = _binding!!


    private val navigationArgs: BankFinalFragmentArgs by navArgs()

    private val bankViewModel: BankViewModel by activityViewModels {
        BankViewModelFactory(
            (activity?.application as EmailApplication).bankDatabase.bankAccountDao()
        )
    }

    lateinit var bankAccount: BankAccount

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBankOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val autoTextViewBank: AutoCompleteTextView = binding.bankName
//        binding.apply {
//            viewModel = sharedViewModel
//            lifecycleOwner = viewLifecycleOwner
//            fragmentBankOne = this@BankOneFragment
//        }
        val bankName: Array<out String> = resources.getStringArray(R.array.BankNames)
        val bankNamearrayAdapter =
            ArrayAdapter(this.requireContext(), android.R.layout.simple_list_item_1, bankName)
        autoTextViewBank.setAdapter(bankNamearrayAdapter)

        val autoTextView: AutoCompleteTextView = binding.accountType
        val typeOfAccount: Array<out String> = resources.getStringArray(R.array.AccountTypes)
        val arrayAdapter =
            ArrayAdapter(this.requireContext(), android.R.layout.simple_list_item_1, typeOfAccount)
        autoTextView.setAdapter(arrayAdapter)


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

        val id = navigationArgs.itemId

        if (id > 0) {
            // edit account
            bankViewModel.retrieveAccount(id).observe(this.viewLifecycleOwner) { selectedAccount ->
                bankAccount = selectedAccount
                bind(bankAccount)
            }
        }else {
            binding.apply {
                atmCardOptions.check(R.id.atm_card_option_yes)
                upiOption.check(R.id.upi_option_yes)
                bankingAppOption.check(R.id.banking_app_option_yes)

                submitButtonBankTwo.setOnClickListener {
                    addBankAccount()
                }
            }
            }

        binding.atmCardOptionYes.setOnClickListener {
            yesOptionCardClick()
        }
        binding.atmCardOptionNo.setOnClickListener {
            noOptionCardClick()
        }
        binding.upiOptionYes.setOnClickListener {
            yesUpiOptionClick()
        }
        binding.upiOptionNo.setOnClickListener {
            noUpiOptionClick()
        }
        binding.bankingAppOptionYes.setOnClickListener {
            yesBankingAppOptionClick()
        }
        binding.bankingAppOptionNo.setOnClickListener {
            noBankingAppOptionClick()
        }
    }
    private fun bind(bankAccount: BankAccount) {
        binding.apply {
            bankName.setText(bankAccount.bankName, TextView.BufferType.SPANNABLE)
            accountHolderName.setText(bankAccount.accountHolderName, TextView.BufferType.SPANNABLE)
            accountType.setText(bankAccount.accountType, TextView.BufferType.SPANNABLE)
            accountNo.setText(bankAccount.accountNo, TextView.BufferType.SPANNABLE)
            ifscCode.setText(bankAccount.ifscNo, TextView.BufferType.SPANNABLE)
            mobileNo.setText(bankAccount.regMobileNo, TextView.BufferType.SPANNABLE)
            bankEmail.setText(bankAccount.regEmailId, TextView.BufferType.SPANNABLE)
            bankRemarks.setText(bankAccount.accountRemarks, TextView.BufferType.SPANNABLE)
            cardHolderName.setText(bankAccount.nameOnCard, TextView.BufferType.SPANNABLE)
            cardNo.setText(bankAccount.cardNumber, TextView.BufferType.SPANNABLE)
            cardCvv.setText(bankAccount.cardCVV, TextView.BufferType.SPANNABLE)
            cardExpiryDate.setText(bankAccount.cardExpiryDate, TextView.BufferType.SPANNABLE)
            atmPin.setText(bankAccount.cardAtmPin, TextView.BufferType.SPANNABLE)
            upiPin.setText(bankAccount.upiPin, TextView.BufferType.SPANNABLE)
            bankingAppLogin.setText(bankAccount.mobileLoginPin, TextView.BufferType.SPANNABLE)
            bankingAppPassword.setText(bankAccount.transactionPin, TextView.BufferType.SPANNABLE)
            submitButtonBankTwo.setOnClickListener {
                updateAccount()
            }
        }
        if (bankAccount.haveCard) {
            binding.atmCardOptions.check(R.id.atm_card_option_yes)
            unhideCardView()
        }else {
            binding.atmCardOptions.check(R.id.atm_card_option_no)
            hideCardView()
        }
        if (bankAccount.haveUpi) {
            binding.upiOption.check(R.id.upi_option_yes)
            unhideUPIView()
        }else {
            binding.upiOption.check(R.id.upi_option_no)
            hideUPIView()
        }
        if (bankAccount.haveApp) {
            binding.bankingAppOption.check(R.id.banking_app_option_yes)
            unhideAppView()
        }else {
            binding.bankingAppOption.check(R.id.banking_app_option_no)
            hideAppView()
        }

    }
    private fun addBankAccount() {
        if (isEntryValid()) {
            bankViewModel.addNewAccount(
                binding.bankName.text.toString(),
                binding.accountHolderName.text.toString(),
                binding.accountType.text.toString(),
                binding.accountNo.text.toString(),
                binding.ifscCode.text.toString(),
                binding.mobileNo.text.toString(),
                binding.bankEmail.text.toString(),
                binding.bankRemarks.text.toString(),
                binding.cardHolderName.text.toString(),
                binding.cardNo.text.toString(),
                binding.cardExpiryDate.text.toString(),
                binding.cardCvv.text.toString(),
                binding.atmPin.text.toString(),
                binding.upiPin.text.toString(),
                binding.bankingAppLogin.text.toString(),
                binding.bankingAppPassword.text.toString(),
                binding.atmCardOptionYes.isChecked,
                binding.upiOptionYes.isChecked,
                binding.bankingAppOptionYes.isChecked
            )
            val action = BankOneFragmentDirections.actionBankOneFragmentToBankCategoryFragment()
            findNavController().navigate(action)
        }
    }
    private fun isEntryValid(): Boolean {
        return bankViewModel.isEntryValid(
            binding.bankName.text.toString(),
            binding.accountHolderName.text.toString()
        )
    }

    private fun updateAccount() {
        if (isEntryValid()) {
            bankViewModel.updateAccount(
                this.navigationArgs.itemId,
                this.binding.bankName.text.toString(),
                this.binding.accountHolderName.text.toString(),
                this.binding.accountType.text.toString(),
                this.binding.accountNo.text.toString(),
                this.binding.ifscCode.text.toString(),
                this.binding.mobileNo.text.toString(),
                this.binding.bankEmail.text.toString(),
                this.binding.bankRemarks.text.toString(),
                this.binding.cardHolderName.text.toString(),
                this.binding.cardNo.text.toString(),
                this.binding.cardExpiryDate.text.toString(),
                this.binding.cardCvv.text.toString(),
                this.binding.atmPin.text.toString(),
                this.binding.upiPin.text.toString(),
                this.binding.bankingAppLogin.text.toString(),
                this.binding.bankingAppPassword.text.toString(),
                this.binding.atmCardOptionYes.isChecked,
                this.binding.upiOptionYes.isChecked,
                this.binding.bankingAppOptionYes.isChecked
            )
            val action = BankOneFragmentDirections.actionBankOneFragmentToBankFinalFragment(this.navigationArgs.itemId)
            findNavController().navigate(action)
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
        val constraintLayout: ConstraintLayout = binding.bankLayout
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
        val constraintLayout: ConstraintLayout = binding.bankLayout
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
        val constraintLayout: ConstraintLayout = binding.bankLayout
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
        val constraintLayout: ConstraintLayout = binding.bankLayout
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
        val constraintLayout: ConstraintLayout = binding.bankLayout
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
        val constraintLayout: ConstraintLayout = binding.bankLayout
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

    fun noOptionCardClick() {
        val noOptionCard: RadioButton = binding.atmCardOptionNo
        if (noOptionCard.isChecked) {
            hideCardView()
        }
    }
    fun yesOptionCardClick() {
        val yesOptionCard: RadioButton = binding.atmCardOptionYes
        if (yesOptionCard.isChecked) {
            unhideCardView()
        }
    }

    fun noUpiOptionClick() {
        val noOptionUPI: RadioButton = binding.upiOptionNo
        if (noOptionUPI.isChecked) {
            hideUPIView()
        }
    }

    fun yesUpiOptionClick() {
        val yesOptionUPI: RadioButton = binding.upiOptionYes
        if (yesOptionUPI.isChecked) {
            unhideUPIView()
        }
    }

    fun noBankingAppOptionClick() {
        val noOptionApp: RadioButton = binding.bankingAppOptionNo
        if (noOptionApp.isChecked) {
            hideAppView()
        }
    }

    fun yesBankingAppOptionClick() {
        val yesOptionApp: RadioButton = binding.bankingAppOptionYes
        if (yesOptionApp.isChecked) {
            unhideAppView()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}