package com.example.organize


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.organize.adapter.SocialMediaAdapter
import com.example.organize.adapter.SocialMediaAdapter2
import com.example.organize.data.SettingsDataStore
import com.example.organize.data.SocialMediaAccount
import com.example.organize.databinding.FragmentSocialMediaCategoryBinding
import com.example.organize.model.CategoryViewModel
import com.example.organize.model.SocialMediaViewModel
import com.example.organize.model.SocialMediaViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class SocialMediaCategoryFragment : Fragment() {
    private var _binding: FragmentSocialMediaCategoryBinding? = null
    private val binding get() = _binding!!
    // keep track if predefined Social Media Accounts are there or not
    private var isSocials = true

    private lateinit var SettingsDataStore: SettingsDataStore

    private val socialMediaViewModel: SocialMediaViewModel by activityViewModels {
        SocialMediaViewModelFactory(
            (activity?.application as EmailApplication).socialMediaDatabase.socialMediaDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSocialMediaCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = SocialMediaAdapter2 {
            val action = SocialMediaCategoryFragmentDirections.actionSocialMediaCategoryFragmentToSocialMediaFinalFragment(it.id)
            this.findNavController().navigate(action)
        }
        binding.socialMediaRecyclerView.adapter = adapter
        socialMediaViewModel.allSocialMediaAccounts.observe(this.viewLifecycleOwner) { accounts ->
            accounts.let {
                adapter.submitList(it)
            }
        }
        binding.createNewCardButtonSocialmedia.setOnClickListener {
            goToAddAccountDetails()
        }

        // Initialize SettingsDataStore
        SettingsDataStore = SettingsDataStore(requireContext())
        SettingsDataStore.prefereceFlow.asLiveData().observe(viewLifecycleOwner) { value ->
            isSocials = value
            addSocials()
        }
    }

    private fun addSocials() {
        if (isSocials) {
            val socials = listOf<SocialMediaAccount>(
                SocialMediaAccount(accountTitle = getString(R.string.facebook), accountLogin = getString(R.string.facebook_id), accountPassword = getString(R.string.facebook_password), accountRemarks = getString(R.string.facebook_remarks), accountIcon = R.drawable.facebook_icon),
                SocialMediaAccount(accountTitle = getString(R.string.instagram), accountLogin = getString(R.string.username), accountPassword = getString(R.string.instagram_password), accountRemarks = getString(R.string.instagram_remarks), accountIcon = R.drawable.ig_icon),
                SocialMediaAccount(accountTitle = getString(R.string.twitter), accountLogin = getString(R.string.username), accountPassword = getString(R.string.twitter_password), accountRemarks = getString(R.string.twitter_remarks), accountIcon = R.drawable.twitter_icon),
                SocialMediaAccount(accountTitle = getString(R.string.linkedin), accountLogin = getString(R.string.facebook_id), accountPassword = getString(R.string.linkedin_password), accountRemarks = getString(R.string.linkedin_remarks), accountIcon = R.drawable.linkedin_icon),
                SocialMediaAccount(accountTitle = getString(R.string.snapchat), accountLogin = getString(R.string.username), accountPassword = getString(R.string.snapchat_password), accountRemarks = getString(R.string.snapchat_remarks), accountIcon = R.drawable.snapchat)
                )
            socialMediaViewModel.insertItems(socials)
            isSocials = false
            // Launch a coroutine and write the layout setting in the preference Datastore
            lifecycleScope.launch {
                SettingsDataStore.saveLayoutToPreferenceStore(isSocials, requireContext())
            }
        }
    }
    private fun goToAddAccountDetails() {
        val action = SocialMediaCategoryFragmentDirections.actionSocialMediaCategoryFragmentToSocialMediaOneFragment(getString(R.string.sm_account_details))
        this.findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}