package com.example.organize


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.organize.databinding.FragmentSnapchatEditBinding
import com.example.organize.model.CategoryViewModel
import com.google.android.material.textfield.TextInputEditText

class SnapchatEditFragment : Fragment() {

    private var _binding: FragmentSnapchatEditBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: CategoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSnapchatEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            fragmentSnapchatEdit = this@SnapchatEditFragment
        }
    }

    fun saveSnapchatAccountDetails() {
        val sctitle = binding.scEditTitle.editableText.toString()
        val sclogin = binding.scEditLogin.editableText.toString()
        val scpassword = binding.scEditPassword.editableText.toString()
        val scremarks = binding.scEditRemarks.editableText.toString()
        sharedViewModel.setSnapchatAccount(sctitle, sclogin, scpassword, scremarks)
        findNavController().navigate(R.id.action_snapchatEditFragment_to_snapchatFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}