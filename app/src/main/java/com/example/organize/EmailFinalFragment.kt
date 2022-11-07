package com.example.organize

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.organize.data.EmailAccount
import com.example.organize.databinding.FragmentEmailFinalBinding
import com.example.organize.model.EmailViewModel
import com.example.organize.model.EmailViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class EmailFinalFragment : Fragment() {
    private var _binding: FragmentEmailFinalBinding? = null
    private val binding get() = _binding!!

    private val navigationargs: EmailFinalFragmentArgs by navArgs()

    private val emailViewModel: EmailViewModel by activityViewModels {
        EmailViewModelFactory(
            (activity?.application as EmailApplication).database.emailDao()
        )
    }

    lateinit var emailAccount: EmailAccount
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmailFinalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationargs.itemId
        emailViewModel.retrieveEmail(id).observe(this.viewLifecycleOwner) { selectedEmailAccount ->
            emailAccount = selectedEmailAccount
            bind(emailAccount)
        }
    }
    private fun bind(emailAccount: EmailAccount) {
        binding.apply {
            emailTitleFinal.text = emailAccount.accountTitle
            emailIdFinal.text = emailAccount.accountEmail
            emailPasswordFinal.text = emailAccount.accountPassword
            emailRemarksFinal.text = emailAccount.accountRemarks
            editButtonEmailFinal.setOnClickListener {
                editEmailAccount()
            }
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
                    binding.emailTitleFinal.text.toString(),
                    binding.emailIdFinal.text.toString(),
                    binding.emailPasswordFinal.text.toString(),
                    binding.emailRemarksFinal.text.toString())
                val intent = Intent(Intent.ACTION_SEND)
                    .setType("text/plain")
                    .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_ac_details))
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
    private fun deleteEmailAccount() {
        emailViewModel.deleteItem(emailAccount)
        findNavController().navigateUp()
        Snackbar.make(binding.root, R.string.email_account_deleted, Snackbar.LENGTH_SHORT).show()
    }

    /**
     * Displays an alert dialog to get the user's confirmation before deleting the item.
     */
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question_email))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteEmailAccount()
            }
            .show()
    }
    private fun editEmailAccount() {
        val action = EmailFinalFragmentDirections.actionEmailFinalFragmentToEmailOneFragment(getString(R.string.edit_email_details),emailAccount.id)
        this.findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}