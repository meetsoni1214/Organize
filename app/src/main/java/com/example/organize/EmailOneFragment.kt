package com.example.organize

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.organize.data.EmailAccount
import com.example.organize.databinding.FragmentEmailOneBinding
import com.example.organize.model.EmailViewModel
import com.example.organize.model.EmailViewModelFactory


class EmailOneFragment : Fragment() {
    private var _binding: FragmentEmailOneBinding? = null
    private val binding get() = _binding!!

    private val navigationArgs: EmailFinalFragmentArgs by navArgs()


    private val emailViewModel: EmailViewModel by activityViewModels {
        EmailViewModelFactory(
            (activity?.application as EmailApplication).database.emailDao()
        )
    }

    lateinit var emailAccount: EmailAccount

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmailOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.itemId

        if (id > 0) {
            //edit email
            emailViewModel.retrieveEmail(id).observe(this.viewLifecycleOwner) { selectedEmailAccount ->
                emailAccount = selectedEmailAccount
                bind(emailAccount)
            }
        }else {
            binding.emailSubmitButton.setOnClickListener {
                addEmailAccount()
            }
        }
    }
    private fun isEntryValid(): Boolean {
        return emailViewModel.isEntryValid(
            binding.eTitle.text.toString(),
            binding.eLogin.text.toString(),
            binding.ePassword.text.toString(),
            binding.eRemarks.text.toString()
        )
    }
    private fun addEmailAccount() {
            if (isEntryValid()) {
                emailViewModel.addNewEmail(
                    binding.eTitle.text.toString(),
                    binding.eLogin.text.toString(),
                    binding.ePassword.text.toString(),
                    binding.eRemarks.text.toString()
                )

                val action = EmailOneFragmentDirections.actionEmailOneFragmentToEmailCategoryFragment()
                findNavController().navigate(action)
            }
    }
    private fun bind(emailAccount: EmailAccount) {
        binding.apply {
            eTitle.setText(emailAccount.accountTitle, TextView.BufferType.SPANNABLE)
            eLogin.setText(emailAccount.accountEmail, TextView.BufferType.SPANNABLE)
            ePassword.setText(emailAccount.accountPassword, TextView.BufferType.SPANNABLE)
            eRemarks.setText(emailAccount.accountRemarks, TextView.BufferType.SPANNABLE)
            emailSubmitButton.setOnClickListener {
                updateEmailAccount()
            }
        }
    }
    private fun updateEmailAccount() {
        if (isEntryValid()) {
            emailViewModel.updateEmailAccount(
                this.navigationArgs.itemId,
                this.binding.eTitle.text.toString(),
                this.binding.eLogin.text.toString(),
                this.binding.ePassword.text.toString(),
                this.binding.eRemarks.text.toString()
            )
            val action = EmailOneFragmentDirections.actionEmailOneFragmentToEmailFinalFragment(this.navigationArgs.itemId)
            findNavController().navigate(action)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}