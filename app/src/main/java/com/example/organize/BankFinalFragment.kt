package com.example.organize

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.organize.databinding.FragmentBankFinalBinding
import com.example.organize.model.CategoryViewModel
import com.example.organize.model.SelectedStatus

class BankFinalFragment : Fragment() {

    private var _binding: FragmentBankFinalBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: CategoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBankFinalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            fragmentbankfinal = this@BankFinalFragment
        }
        val cardStatus: SelectedStatus? = sharedViewModel.atmCardStatus.value
        if (cardStatus == SelectedStatus.NO) {
            hideCardView()
        }
        val upiPinStatus: SelectedStatus? = sharedViewModel.upiStatus.value
        if (upiPinStatus == SelectedStatus.NO) {
            hideUpiView()
        }
        val bankingAppStatus: SelectedStatus? = sharedViewModel.mobileAppStatus.value
        if (bankingAppStatus == SelectedStatus.NO) {
            hideAppView()
        }
    }

    private fun hideAppView() {
        val checkCardStatus = sharedViewModel.atmCardStatus.value
        val checkUpiStatus = sharedViewModel.mobileAppStatus.value
        binding.acMobPinTitle.visibility = View.GONE
        binding.acMobPin.visibility = View.GONE
        binding.acTransactionPinTitle.visibility = View.GONE
        binding.acTransactionPin.visibility = View.GONE
    }

    private fun hideUpiView() {
        val checkCardStatus = sharedViewModel.atmCardStatus.value
        val checkMAppStatus = sharedViewModel.mobileAppStatus.value
        binding.acUpiPinTitle.visibility = View.GONE
        binding.acUpiPin.visibility = View.GONE
        val constraintLayout: ConstraintLayout = binding.bankFinalFragmentLayout
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        if (checkCardStatus == SelectedStatus.YES && checkMAppStatus == SelectedStatus.YES) {
            constraintSet.connect(
                R.id.ac_mob_pin_title,
                ConstraintSet.TOP,
                R.id.divider3,
                ConstraintSet.BOTTOM,
                12
            )
        }
        if (checkCardStatus == SelectedStatus.YES && checkMAppStatus == SelectedStatus.NO) {
            binding.divider3.visibility = View.GONE
        }

        constraintSet.applyTo(constraintLayout)
    }

    fun gotoEditScreen() {
        findNavController().navigate(R.id.action_bankFinalFragment_to_bankOneFragment)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun hideCardView() {
        val checkUpiStatus = sharedViewModel.upiStatus.value
        val checkMAppStatus = sharedViewModel.mobileAppStatus.value
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
        if (checkUpiStatus == SelectedStatus.YES && checkMAppStatus == SelectedStatus.YES) {
            constraintSet.connect(
                R.id.divider3,
                ConstraintSet.TOP,
                R.id.ac_email,
                ConstraintSet.BOTTOM,
                12
            )
        }else if (checkUpiStatus == SelectedStatus.NO && checkMAppStatus == SelectedStatus.YES)  {
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
                R.id.ac_email,
                ConstraintSet.BOTTOM,
                12
            )
        }else if (checkUpiStatus == SelectedStatus.YES && checkMAppStatus == SelectedStatus.NO) {
            constraintSet.connect(
                R.id.divider3,
                ConstraintSet.TOP,
                R.id.ac_email,
                ConstraintSet.BOTTOM,
                12
            )
        }else {
            binding.divider3.visibility = View.GONE
        }
        constraintSet.applyTo(constraintLayout)
    }

}