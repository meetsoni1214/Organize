package com.example.organize


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.organize.adapter.EmailListAdapter
import com.example.organize.databinding.FragmentEmailCategoryBinding
import com.example.organize.model.EmailViewModel
import com.example.organize.model.EmailViewModelFactory

enum class HasEmailAccounts {YES, NO}

class EmailCategoryFragment : Fragment() {
    private var _binding: FragmentEmailCategoryBinding? = null
    private val binding get() = _binding!!

    private val emailViewModel: EmailViewModel by activityViewModels {
        EmailViewModelFactory(
            (activity?.application as EmailApplication).database.emailDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmailCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = EmailListAdapter {
            val action = EmailCategoryFragmentDirections.actionEmailCategoryFragmentToEmailFinalFragment(it.id)
            this.findNavController().navigate(action)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        emailViewModel.allEmails.observe(this.viewLifecycleOwner) { emails ->
            emails.let {
                adapter.submitList(it)
            }
        }

//        val textView: TextView = binding.categoryScreenTextEmail
//        textView.text = getString(R.string.category_screen_text_email)
        binding.createNewCardButtonEmail.setOnClickListener {
            goToAddAccountDetails()
        }
    }

    private fun goToAddAccountDetails() {
        val action = EmailCategoryFragmentDirections.actionEmailCategoryFragmentToEmailOneFragment(getString(R.string.sm_account_details))
        this.findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}