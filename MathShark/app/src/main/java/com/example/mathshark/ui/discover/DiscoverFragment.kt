package com.example.mathshark.ui.discover

import ThemeListAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mathshark.databinding.FragmentDiscoverBinding
import com.example.mathshark.index.SharedDataViewModel

class DiscoverFragment : Fragment() {

    private lateinit var sharedDataViewModel: SharedDataViewModel
    private var _binding: FragmentDiscoverBinding? = null
    private lateinit var themeListAdapter: ThemeListAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        sharedDataViewModel = ViewModelProvider(requireActivity()).get(SharedDataViewModel::class.java)

        themeListAdapter = ThemeListAdapter(requireContext(), listOf())
        binding.listTheme.adapter = themeListAdapter
        binding.listTheme.layoutManager = GridLayoutManager(requireContext(), 2)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                themeListAdapter.filter(newText ?: "")
                return true
            }
        })

        sharedDataViewModel.themes.observe(viewLifecycleOwner) { themes ->
            themeListAdapter.updateData(themes)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

