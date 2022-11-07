package com.example.organize


import android.os.Bundle
import android.provider.Telephony.Carriers.PASSWORD
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.organize.databinding.FragmentLinkedInEditBinding
import com.example.organize.model.CategoryViewModel
import com.google.android.material.textfield.TextInputEditText

class LinkedInEditFragment : Fragment() {

    private var _binding: FragmentLinkedInEditBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: CategoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLinkedInEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            fragmentLinkedinEdit = this@LinkedInEditFragment
        }
    }

    fun saveLinkedInAccountDetails() {
        val lititle = binding.liEditTitle.editableText.toString()
        val lilogin = binding.liEditLogin.editableText.toString()
        val lipassword = binding.liEditPassword.editableText.toString()
        val liremarks = binding.liEditRemarks.editableText.toString()
        sharedViewModel.setLinkedInAccount(lititle, lilogin, lipassword, liremarks)
        findNavController().navigate(R.id.action_linkedInEditFragment_to_linkedInFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}