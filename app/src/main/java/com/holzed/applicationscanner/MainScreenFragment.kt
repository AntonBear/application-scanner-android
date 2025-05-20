package com.holzed.applicationscanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.holzed.applicationscanner.databinding.FragmentMainScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreenFragment : Fragment(R.layout.fragment_main_screen) {
    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
