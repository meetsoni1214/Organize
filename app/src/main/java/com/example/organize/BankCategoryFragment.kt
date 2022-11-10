package com.example.organize


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.organize.adapter.BankAccountAdapter
import com.example.organize.databinding.FragmentBankCategoryBinding
import com.example.organize.model.BankViewModel
import com.example.organize.model.BankViewModelFactory
import com.example.organize.model.CategoryViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BankCategoryFragment : Fragment() {
    private var _binding: FragmentBankCategoryBinding? = null
    private val binding get() = _binding!!

    private val bankViewModel: BankViewModel by activityViewModels {
        BankViewModelFactory(
            (activity?.application as EmailApplication).bankDatabase.bankAccountDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBankCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val textView: TextView = binding.categoryScreenTextBank
//        textView.text = getString(R.string.category_screen_text_bank)

//        binding.apply {
//            viewModel = sharedViewModel
//            lifecycleOwner = viewLifecycleOwner
//            fragmentBankCategory = this@BankCategoryFragment
//        }
        val adapter = BankAccountAdapter {
            val action = BankCategoryFragmentDirections.actionBankCategoryFragmentToBankFinalFragment(it.id)
            this.findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        bankViewModel.allAccounts.observe(this.viewLifecycleOwner) { bankAccounts ->
            bankAccounts.let {
                adapter.submitList(it)
            }
        }
        binding.createNewCardButtonBank.setOnClickListener {
            goToAddAccountScreen()
        }
    }
    private fun goToAddAccountScreen() {
        val action = BankCategoryFragmentDirections.actionBankCategoryFragmentToBankOneFragment(getString(R.string.add_bank_account))
        this.findNavController().navigate(action)
    }

}