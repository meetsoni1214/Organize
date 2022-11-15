package com.example.organize

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.organize.data.BankAccount
import com.example.organize.databinding.FragmentBankFinalBinding
import com.example.organize.model.BankViewModel
import com.example.organize.model.BankViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlin.math.exp

class BankFinalFragment : Fragment() {

    private var _binding: FragmentBankFinalBinding? = null
    private val binding get() = _binding!!

    private val navigationargs:BankFinalFragmentArgs by navArgs()

    private val bankViewModel: BankViewModel by activityViewModels {
        BankViewModelFactory(
            (activity?.application as EmailApplication).bankDatabase.bankAccountDao()
        )
    }
    lateinit var bankAccount: BankAccount

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBankFinalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationargs.itemId
        bankViewModel.retrieveAccount(id).observe(this.viewLifecycleOwner) { selectedAccount ->
            bankAccount = selectedAccount
            bind(bankAccount)
        }

    }

    private fun bind(bankAccount: BankAccount) {
        binding.apply {
            bankName.text = bankAccount.bankName
            acHolderName.text = bankAccount.accountHolderName
            acType.text = bankAccount.accountType
            acNo.text = bankAccount.accountNo
            ifscNo.text = bankAccount.ifscNo
            acMob.text = bankAccount.regMobileNo
            acEmail.text = bankAccount.regEmailId
            acRemarks.text = bankAccount.accountRemarks
            acCardName.text = bankAccount.nameOnCard
            acCardNo.text = bankAccount.cardNumber
            acCardExpiryDate.text = bankAccount.cardExpiryDate
            acCardCvv.text = bankAccount.cardCVV
            acCardAtmPin.text = bankAccount.cardAtmPin
            acUpiPin.text = bankAccount.upiPin
            acMobPin.text = bankAccount.mobileLoginPin
            acTransactionPin.text = bankAccount.transactionPin
            editButtonBank.setOnClickListener {
                editBankAccount()
            }
        }
            when (bankAccount.bankName) {
                getString(R.string.bank_of_baroda) -> {
                    binding.bankLogo.setImageResource(R.drawable.bank_of_baroda_logo)
                }
                getString(R.string.kalupur_bank) -> {
                    binding.bankLogo.setImageResource(R.drawable.kalupur_bank_logo)
                }
                getString(R.string.state_bank_india) -> {
                    binding.bankLogo.setImageResource(R.drawable.sbi_logo)
                }
                getString(R.string.bank_of_india) -> {
                    binding.bankLogo.setImageResource(R.drawable.boi)
                }
                getString(R.string.union_bank) -> {
                    binding.bankLogo.setImageResource(R.drawable.union_bank)
                }
                getString(R.string.icici_bank) -> {
                    binding.bankLogo.setImageResource(R.drawable.icici_logo)
                }
                else -> {
                    binding.bankLogo.setImageResource(R.drawable.bank_image_2)
                }
            }
                if (!(bankAccount.haveCard)) {
            hideCardView()
        }
        if (!(bankAccount.haveUpi)) {
            hideUpiView()
        }
        if (!(bankAccount.haveApp)) {
            hideAppView()
        }
    }
    private fun editBankAccount() {
        val action = BankFinalFragmentDirections.actionBankFinalFragmentToBankOneFragment(getString(R.string.edit_bank_ac_details), bankAccount.id)
        this.findNavController().navigate(action)
    }

    private fun hideAppView() {
        binding.acMobPinTitle.visibility = View.GONE
        binding.acMobPin.visibility = View.GONE
        binding.acTransactionPinTitle.visibility = View.GONE
        binding.acTransactionPin.visibility = View.GONE
    }

    private fun hideUpiView() {
        val checkCardStatus = bankAccount.haveCard
        val checkMAppStatus = bankAccount.haveApp
        binding.acUpiPinTitle.visibility = View.GONE
        binding.acUpiPin.visibility = View.GONE
        val constraintLayout: ConstraintLayout = binding.bankFinalFragmentLayout
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        if (checkCardStatus && checkMAppStatus) {
            constraintSet.connect(
                R.id.ac_mob_pin_title,
                ConstraintSet.TOP,
                R.id.divider3,
                ConstraintSet.BOTTOM,
                12
            )
        }
        if (checkCardStatus && !(checkMAppStatus)) {
            binding.divider3.visibility = View.GONE
        }

        constraintSet.applyTo(constraintLayout)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_record -> {
                showConfirmationDialog()
                return true
            }
            R.id.action_share -> {
                showShareDialog()
                return true
            }else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Displays an alert dialog to get the user's confirmation before deleting the item.
     */
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question_bank))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteAccount()
            }
            .show()
    }
    private fun showShareDialog() {
        val inflater = requireActivity().layoutInflater
        val dialogBinding = inflater.inflate(R.layout.share_bank_details_dialog, null)
        val alertDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.share_title))
            .setMessage(getString(R.string.share_desc))
            .setCancelable(false)
            .setView(dialogBinding)
            .show()
        val shareButton = dialogBinding.findViewById<TextView>(R.id.share_text)
        val cancelButton = dialogBinding.findViewById<TextView>(R.id.cancel_text)

        cancelButton.setOnClickListener {
            alertDialog.dismiss()
        }
        shareButton.setOnClickListener {
            shareDetails(dialogBinding)
        }

    }
    private fun shareDetails(dialogBinding: View) {
        val selectedItems = ArrayList<String>()

        val bankName = dialogBinding.findViewById<CheckBox>(R.id.bank_name_cb)
        val acHolderName = dialogBinding.findViewById<CheckBox>(R.id.account_holder_name_cb)
        val acType = dialogBinding.findViewById<CheckBox>(R.id.account_type_cb)
        val acNo = dialogBinding.findViewById<CheckBox>(R.id.account_number_cb)
        val ifscNo = dialogBinding.findViewById<CheckBox>(R.id.ifs_no_cb)
        val regMobNo = dialogBinding.findViewById<CheckBox>(R.id.reg_mob_no_cb)
        val regEmail = dialogBinding.findViewById<CheckBox>(R.id.reg_email_cb)
        val nameOnCard = dialogBinding.findViewById<CheckBox>(R.id.card_name_cb)
        val cardNo = dialogBinding.findViewById<CheckBox>(R.id.card_no_cb)
        val expDate = dialogBinding.findViewById<CheckBox>(R.id.card_expiry_date_cb)
        val cvv = dialogBinding.findViewById<CheckBox>(R.id.card_cvv_cb)
        val cardPin = dialogBinding.findViewById<CheckBox>(R.id.atm_pin_cb)
        val upiPin = dialogBinding.findViewById<CheckBox>(R.id.upi_pin_cb)
        val appLogin = dialogBinding.findViewById<CheckBox>(R.id.app_login_cb)
        val tPin = dialogBinding.findViewById<CheckBox>(R.id.transaction_pin_cb)
        val remarks = dialogBinding.findViewById<CheckBox>(R.id.remarks_cb)

        if (bankName.isChecked) {
            selectedItems.add("${getString(R.string.bank_name)}: ${binding.bankName.text.toString()}\n")
        }
        if (acHolderName.isChecked) {
            selectedItems.add("${binding.acHolderNameTitle.text}: ${binding.acHolderName.text}\n")
        }
        if (acType.isChecked) {
            selectedItems.add("${getString(R.string.account_type)}: ${binding.acType.text}\n")
        }
        if (acNo.isChecked) {
            selectedItems.add("${getString(R.string.account_no)}: ${binding.acNo.text}\n")
        }
        if (ifscNo.isChecked) {
            selectedItems.add("${getString(R.string.ifsc_code)}: ${binding.ifscNo.text}\n")
        }
        if (regMobNo.isChecked) {
            selectedItems.add("${getString(R.string.mobile_no)}: ${binding.acMob.text}\n")
        }
        if (regEmail.isChecked) {
            selectedItems.add("${getString(R.string.bank_email)}: ${binding.acEmail.text}\n")
        }
        if (remarks.isChecked) {
            selectedItems.add("${binding.acRemarksTitle.text}: ${binding.acRemarks.text}\n")
        }
        if (nameOnCard.isChecked) {
            selectedItems.add("${getString(R.string.name_on_card)}: ${binding.acCardName.text}\n")
        }
        if (cardNo.isChecked) {
            selectedItems.add("${getString(R.string.card_no)}: ${binding.acCardNo.text}\n")
        }
        if (expDate.isChecked) {
            selectedItems.add("${getString(R.string.expiry_date)}: ${binding.acCardExpiryDate.text}\n")
        }
        if (cvv.isChecked) {
            selectedItems.add("${getString(R.string.cvv)}: ${binding.acCardCvv.text}\n")
        }
        if (cardPin.isChecked) {
            selectedItems.add("${getString(R.string.atm_pin)}: ${binding.acCardAtmPin.text}\n")
        }
        if (upiPin.isChecked) {
            selectedItems.add("${getString(R.string.upi_pin)}: ${binding.acUpiPin.text}\n")
        }
        if (appLogin.isChecked) {
            selectedItems.add("${getString(R.string.mobile_login_pin)}: ${binding.acMobPin.text}\n")
        }
        if (tPin.isChecked) {
            selectedItems.add("${getString(R.string.transaction_Pin)}: ${binding.acTransactionPin.text}\n")
        }

        val shareDetails = StringBuilder()
        for (field in selectedItems) {
            shareDetails.append(field)
        }
        val intent = Intent(Intent.ACTION_SEND)
            .setType("text/plain")
            .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.bank_ac_details))
            .putExtra(Intent.EXTRA_TEXT, shareDetails.toString())

        if (activity?.packageManager?.resolveActivity(intent, 0) != null) {
            startActivity(intent)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun deleteAccount() {
        bankViewModel.deleteItem(bankAccount)
        findNavController().navigateUp()
        Snackbar.make(binding.root, R.string.bank_account_delete, Snackbar.LENGTH_SHORT ).show()
    }

    private fun hideCardView() {
        val checkUpiStatus = bankAccount.haveUpi
        val checkMAppStatus = bankAccount.haveApp
        binding.acCardNameTitle.visibility = View.GONE
        binding.acCardName.visibility = View.GONE
        binding.acCardNoTitle.visibility = View.GONE
        binding.acCardNo.visibility = View.GONE
        binding.acCardCvv.visibility = View.GONE
        binding.acCardCvvTitle.visibility = View.GONE
        binding.acCardExpiryDateTitle.visibility = View.GONE
        binding.acCardExpiryDate.visibility = View.GONE
        binding.acCardAtmPinTitle.visibility = View.GONE
        binding.acCardAtmPin.visibility = View.GONE
        binding.divider2.visibility = View.GONE
        val constraintLayout: ConstraintLayout = binding.bankFinalFragmentLayout
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        if (checkUpiStatus && checkMAppStatus) {
            constraintSet.connect(
                R.id.divider3,
                ConstraintSet.TOP,
                R.id.ac_remarks,
                ConstraintSet.BOTTOM,
                12
            )
        }else if (!(checkUpiStatus) && checkMAppStatus)  {
            constraintSet.connect(
                R.id.ac_mob_pin_title,
                ConstraintSet.TOP,
                R.id.divider3,
                ConstraintSet.BOTTOM,
                12
            )
            constraintSet.connect(
                R.id.divider3,
                ConstraintSet.TOP,
                R.id.ac_remarks,
                ConstraintSet.BOTTOM,
                12
            )
        }else if (checkUpiStatus && !(checkMAppStatus)) {
            constraintSet.connect(
                R.id.divider3,
                ConstraintSet.TOP,
                R.id.ac_remarks,
                ConstraintSet.BOTTOM,
                12
            )
        }else {
            binding.divider3.visibility = View.GONE
        }
        constraintSet.applyTo(constraintLayout)
    }

}