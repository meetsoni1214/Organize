package com.example.organize


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.organize.databinding.FragmentTwitterEditBinding
import com.example.organize.model.CategoryViewModel
import com.google.android.material.textfield.TextInputEditText


class TwitterEditFragment : Fragment() {

    private var _binding: FragmentTwitterEditBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: CategoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTwitterEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            fragmentTwitterEdit = this@TwitterEditFragment
        }
    }


    fun saveTwitterAccountDetails() {
        val twtitle = binding.twEditTitle.editableText.toString()
        val twlogin = binding.twEditLogin.editableText.toString()
        val twpassword = binding.twEditPassword.editableText.toString()
        val twremarks = binding.twEditRemarks.editableText.toString()
        sharedViewModel.setTwitterAccount(twtitle, twlogin, twpassword, twremarks)
        findNavController().navigate(R.id.action_twitterEditFragment_to_twitterFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}