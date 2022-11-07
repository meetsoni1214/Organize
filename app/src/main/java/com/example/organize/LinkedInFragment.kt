package com.example.organize

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.organize.databinding.FragmentLinkedInBinding
import com.example.organize.model.CategoryViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class LinkedInFragment : Fragment() {
    private var _binding: FragmentLinkedInBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: CategoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLinkedInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            fragmentLinkedIn = this@LinkedInFragment
        }
    }
    fun goToEditScreen() {
        findNavController().navigate(R.id.action_linkedInFragment_to_linkedInEditFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}