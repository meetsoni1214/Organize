package com.example.organize

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.organize.data.SocialMediaAccount
import com.example.organize.databinding.FragmentSocialMediaFinalBinding
import com.example.organize.model.CategoryViewModel
import com.example.organize.model.SocialMediaViewModel
import com.example.organize.model.SocialMediaViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class SocialMediaFinalFragment : Fragment() {
    private var _binding: FragmentSocialMediaFinalBinding? = null
    private val binding get()  = _binding!!

    private val navigationargs: SocialMediaFinalFragmentArgs by navArgs()

    private val socialMediaViewModel: SocialMediaViewModel by activityViewModels {
        SocialMediaViewModelFactory(
            (activity?.application as EmailApplication).socialMediaDatabase.socialMediaDao()
        )
    }
    lateinit var socialMediaAccount: SocialMediaAccount
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSocialMediaFinalBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationargs.itemId
        socialMediaViewModel.retrieveAccount(id).observe(this.viewLifecycleOwner) { selectedAccount ->
            socialMediaAccount = selectedAccount
            bind(socialMediaAccount)
        }
    }
    private fun bind(socialMediaAccount: SocialMediaAccount) {
        binding.apply {
            smTitleFinal.text = socialMediaAccount.accountTitle
            smIdFinal.text = socialMediaAccount.accountLogin
            smPasswordFinal.text = socialMediaAccount.accountPassword
            smRemarksFinal.text = socialMediaAccount.accountRemarks
            editButtonSmFinal.setOnClickListener {
                editAccount()
            }
        }
        if (socialMediaAccount.accountIcon != 0) {
            binding.smIconFinal.setImageResource(socialMediaAccount.accountIcon)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)

    }

    //This is called any time a menu item is tapped
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_record -> {
                showConfirmationDialog()
                return true
            }
            R.id.action_share -> {
                val shareDetails = getString(R.string.share_email_detail,
                    binding.smTitleFinal.text.toString(),
                    binding.smIdFinal.text.toString(),
                    binding.smPasswordFinal.text.toString(),
                    binding.smRemarksFinal.text.toString())
                val intent = Intent(Intent.ACTION_SEND)
                    .setType("text/plain")
                    .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.ac_details))
                    .putExtra(Intent.EXTRA_TEXT, shareDetails)

                if (activity?.packageManager?.resolveActivity(intent, 0) != null) {
                    startActivity(intent)
                }
                return true
            }
            else ->  super.onOptionsItemSelected(item)
        }
    }



    /**
     * Deletes the current item and navigates to the list Category fragment.
     */
    private fun deleteAccount() {
        socialMediaViewModel.deleteItem(socialMediaAccount)
        findNavController().navigateUp()
        Snackbar.make(binding.root, R.string.account_deleted, Snackbar.LENGTH_SHORT).show()
    }

    /**
     * Displays an alert dialog to get the user's confirmation before deleting the item.
     */
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question_social))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteAccount()
            }
            .show()
    }
    private fun editAccount() {
        val action = SocialMediaFinalFragmentDirections.actionSocialMediaFinalFragmentToSocialMediaOneFragment(getString(R.string.edit_account_details), socialMediaAccount.id)
        this.findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}