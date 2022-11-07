package com.example.organize

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.organize.data.SocialMediaAccount
import com.example.organize.databinding.FragmentSocialMediaOneBinding
import com.example.organize.model.CategoryViewModel
import com.example.organize.model.SocialMediaViewModel
import com.example.organize.model.SocialMediaViewModelFactory

class SocialMediaOneFragment : Fragment() {
    private var _binding: FragmentSocialMediaOneBinding? = null
    private val binding get() = _binding!!

    private val navigationArgs: SocialMediaFinalFragmentArgs by navArgs()


    private val socialMediaViewModel: SocialMediaViewModel by activityViewModels {
        SocialMediaViewModelFactory(
            (activity?.application as EmailApplication).socialMediaDatabase.socialMediaDao()
        )
    }

    lateinit var socialMediaAccount: SocialMediaAccount
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSocialMediaOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.itemId

        if (id > 0) {
            //edit account
            socialMediaViewModel.retrieveAccount(id)
                .observe(this.viewLifecycleOwner) { selectedAccount ->
                    socialMediaAccount = selectedAccount
                    bind(socialMediaAccount)
                }
        }else {
            binding.smSubmitButton.setOnClickListener {
                addAccount()
            }
        }
    }
    private fun isEntryValid(): Boolean {
        return socialMediaViewModel.isEntryValid(
            binding.smTitle.text.toString(),
            binding.smLogin.text.toString(),
            binding.smPassword.text.toString(),
            binding.smRemarks.text.toString()
        )
    }
     private fun addAccount() {
        if (isEntryValid()) {
            socialMediaViewModel.addNewAccount(
                binding.smTitle.text.toString(),
                binding.smLogin.text.toString(),
                binding.smPassword.text.toString(),
                binding.smRemarks.text.toString()
            )
            val action = SocialMediaOneFragmentDirections.actionSocialMediaOneFragmentToSocialMediaCategoryFragment()
            findNavController().navigate(action)
        }
    }
    private fun bind(account: SocialMediaAccount) {
        binding.apply {
            smTitle.setText(account.accountTitle, TextView.BufferType.SPANNABLE)
            smLogin.setText(account.accountLogin, TextView.BufferType.SPANNABLE)
            smPassword.setText(account.accountPassword, TextView.BufferType.SPANNABLE)
            smRemarks.setText(account.accountRemarks, TextView.BufferType.SPANNABLE)
            smSubmitButton.setOnClickListener {
                updateAccount()
            }
            if (account.accountIcon != 0) {
                smIcon.setImageResource(account.accountIcon)
            }
        }
    }
    private fun updateAccount() {
        if (isEntryValid()) {
            socialMediaViewModel.updateAccount(
                this.navigationArgs.itemId,
                this.binding.smTitle.text.toString(),
                this.binding.smLogin.text.toString(),
                this.binding.smPassword.text.toString(),
                this.binding.smRemarks.text.toString(),
                socialMediaAccount.accountIcon
            )
            val action = SocialMediaOneFragmentDirections.actionSocialMediaOneFragmentToSocialMediaFinalFragment(this.navigationArgs.itemId)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}