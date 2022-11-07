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
import com.example.organize.databinding.FragmentBankCategoryBinding
import com.example.organize.model.CategoryViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BankCategoryFragment : Fragment() {
    private var _binding: FragmentBankCategoryBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: CategoryViewModel by activityViewModels()

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
        val textView: TextView = binding.categoryScreenTextBank
        textView.text = getString(R.string.category_screen_text_bank)

        binding.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            fragmentBankCategory = this@BankCategoryFragment
        }
    }
    fun goToAddAccountScreen() {
        sharedViewModel.resetBankAcDetails()
        findNavController().navigate(R.id.action_bankCategoryFragment_to_bankOneFragment)
    }

}