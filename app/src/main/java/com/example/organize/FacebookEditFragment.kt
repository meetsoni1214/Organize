package com.example.organize


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.organize.databinding.FragmentFacebookEditBinding
import com.example.organize.model.CategoryViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class FacebookEditFragment : Fragment() {

    private var _binding: FragmentFacebookEditBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: CategoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFacebookEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            fragmentFacebookEdit = this@FacebookEditFragment
        }
    }
    fun saveFacebookAccountDetails() {
        val fbtitle = binding.fbEditTitle.editableText.toString()
        val fblogin = binding.fbEditLogin.editableText.toString()
        val fbpassword = binding.fbEditPassword.editableText.toString()
        val fbremarks = binding.fbEditRemarks.editableText.toString()
        sharedViewModel.setFacebookAccount(fbtitle, fblogin, fbpassword, fbremarks)
        findNavController().navigate(R.id.action_facebookEditFragment_to_facebookFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}