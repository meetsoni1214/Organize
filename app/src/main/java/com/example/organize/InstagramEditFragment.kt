package com.example.organize


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.organize.databinding.FragmentInstagramEditBinding
import com.example.organize.model.CategoryViewModel
import com.google.android.material.textfield.TextInputEditText

class InstagramEditFragment : Fragment() {

    private var _binding: FragmentInstagramEditBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: CategoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInstagramEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            fragmentInstagramEdit = this@InstagramEditFragment
        }
    }

    fun saveInstagramAccountDetails() {
        val igtitle = binding.igEditTitle.editableText.toString()
        val iglogin = binding.igEditLogin.editableText.toString()
        val igpassword = binding.igEditPassword.editableText.toString()
        val igremarks = binding.igEditRemarks.editableText.toString()
        sharedViewModel.setInstagramAccount(igtitle, iglogin, igpassword, igremarks)
        findNavController().navigate(R.id.action_instagramEditFragment_to_instagramFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}