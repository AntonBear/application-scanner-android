package com.holzed.applicationscanner.ui.appdetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.holzed.applicationscanner.R
import com.holzed.applicationscanner.databinding.FragmentAppDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AppDetailFragment : Fragment(R.layout.fragment_app_detail) {

    private var _binding: FragmentAppDetailBinding? = null

    private val binding get() = _binding!!

    private val viewModel: AppDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAppDetailBinding.bind(view)

        val args = AppDetailFragmentArgs.fromBundle(requireArguments())
        viewModel.setAppDetails(args.title, args.version, args.packageName, args.apkHash)

        collectUiState()

        binding.btnLaunchApp.setOnClickListener {
            val launchIntent = requireContext().packageManager.getLaunchIntentForPackage(args.packageName)
            launchIntent?.let {
                startActivity(it)
            } ?: Toast.makeText(
                requireContext(),
                getString(R.string.cannot_launch_app),
                Toast.LENGTH_SHORT,
            ).show()
        }

        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.appTitle.text = state?.title
                    binding.appVersion.text = state?.version
                    binding.appPackage.text = state?.packageName
                    binding.appHash.text = state?.hash
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
