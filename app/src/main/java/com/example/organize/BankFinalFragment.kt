package com.example.organize

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.organize.data.BankAccount
import com.example.organize.databinding.FragmentBankFinalBinding
import com.example.organize.model.BankViewModel
import com.example.organize.model.BankViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

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