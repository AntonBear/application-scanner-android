package com.holzed.applicationscanner.ui.applicationlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.holzed.applicationscanner.R
import com.holzed.applicationscanner.databinding.FragmentApplicationListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ApplicationListFragment : Fragment(R.layout.fragment_application_list) {
    private var _binding: FragmentApplicationListBinding? = null
    private val binding get() = _binding!!
    val appListAdapter = AppListAdapter { app ->
        val action = ApplicationListFragmentDirections
            .actionApplicationListFragmentToAppDetailFragment(
                title = app.title,
                version = app.version,
                packageName = app.packageName,
                apkHash = app.apkHash.toString(),
            )
        findNavController().navigate(action)
    }
    val viewModel: ApplicationListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentApplicationListBinding.bind(view)
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = appListAdapter
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is ApplicationListState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ApplicationListState.Loaded -> {
                        binding.progressBar.visibility = View.GONE
                        appListAdapter.submitList(state.apps)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadAppList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
