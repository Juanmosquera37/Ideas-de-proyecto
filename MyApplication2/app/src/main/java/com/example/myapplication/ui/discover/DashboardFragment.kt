package com.example.myapplication.ui.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.ThemeAdapter
import java.util.Scanner
import com.example.myapplication.databinding.FragmentDiscoverBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDiscoverBinding? = null
    val listTheme: ArrayList<Theme> = ArrayList()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        readThemeInfo()

        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val adapter = ThemeAdapter(requireContext(), listTheme)
        binding.listTheme.adapter = adapter
        binding.listTheme.layoutManager = GridLayoutManager(requireContext(), 2)
        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun readThemeInfo() {
        val input = Scanner(resources.openRawResource(R.raw.themeinfo))
        while (input.hasNextLine()) {
            val data = input.nextLine().split("|")

            val id = data[0].toInt()
            val titulo = data[1]
            val descripcion = data[2]
            val icono = data[3]
            val tema = data[4]


            val theme = Theme(id, titulo, descripcion, icono, tema)
            listTheme.add(theme)
        }
        input.close()
    }


    data class Theme(val id: Int, val titulo: String, val descripcion: String, val icono: String, val tema: String)
}