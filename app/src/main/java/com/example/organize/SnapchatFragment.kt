package com.example.organize

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.organize.databinding.FragmentSnapchatBinding
import com.example.organize.model.CategoryViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SnapchatFragment : Fragment() {
    private var _binding: FragmentSnapchatBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: CategoryViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            fragmentSnapchat = this@SnapchatFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSnapchatBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun goToEditScreen() {
        findNavController().navigate(R.id.action_snapchatFragment_to_snapchatEditFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}