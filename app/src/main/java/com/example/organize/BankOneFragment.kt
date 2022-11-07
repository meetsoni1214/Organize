package com.example.organize


import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.organize.databinding.FragmentBankOneBinding
import com.example.organize.model.CategoryViewModel

class BankOneFragment : Fragment() {
    private var _binding: FragmentBankOneBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: CategoryViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBankOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val autoTextViewBank: AutoCompleteTextView = binding.bankName
        binding.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            fragmentBankOne = this@BankOneFragment
        }
        val bankName: Array<out String> = resources.getStringArray(R.array.BankNames)
        val bankNamearrayAdapter =
            ArrayAdapter(this.requireContext(), android.R.layout.simple_list_item_1, bankName)
        autoTextViewBank.setAdapter(bankNamearrayAdapter)

        val autoTextView: AutoCompleteTextView = binding.accountType
        val typeOfAccount: Array<out String> = resources.getStringArray(R.array.AccountTypes)
        val arrayAdapter =
            ArrayAdapter(this.requireContext(), android.R.layout.simple_list_item_1, typeOfAccount)
        autoTextView.setAdapter(arrayAdapter)
    }
    fun goToAddMoreDetails() {
        val acHolderName: String = binding.accountHolderName.editableText.toString()
        val bankName: String = binding.bankName.editableText.toString()
        val accountType: String = binding.accountType.editableText.toString()
        val accountNo: String = binding.accountNo.editableText.toString()
        val ifscNo: String = binding.ifscCode.editableText.toString()
        val regMobile: String = binding.mobileNo.editableText.toString()
        val regEmail: String = binding.bankEmail.editableText.toString()
        sharedViewModel.setBankAccount(acHolderName, bankName, accountType, accountNo, ifscNo, regMobile, regEmail)
        findNavController().navigate(R.id.action_bankOneFragment_to_bankTwoFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}