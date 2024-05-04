package com.example.mathshark.ui.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mathshark.R
import com.example.mathshark.databinding.FragmentDiscoverBinding
import com.example.mathshark.ThemeAdapter
import java.io.Serializable
import java.util.Scanner

class DiscoverFragment : Fragment() {

    private var _binding: FragmentDiscoverBinding? = null
    val listTheme: ArrayList<ThemeDataInfo> = ArrayList()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        readThemeInfo()
		readLessonInfo()

        val discoverViewModel =
            ViewModelProvider(this).get(DiscoverViewModel::class.java)

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
            val color = data[5]

            val theme = ThemeDataInfo(id, titulo, descripcion, icono, tema, mutableListOf(), color)
            listTheme.add(theme)
        }
        input.close()
    }

    fun readLessonInfo() {
        val input = Scanner(resources.openRawResource(R.raw.lessoninfo))
        while (input.hasNextLine()) {
            val data = input.nextLine().split("|")

            val id = data[0].toInt()
            val titulo = data[1]
            val descripcion = data[2]
            val imagen = data[3]
            val informacion = data[4]
            val isExpanded = data[5].toBoolean()
            val themeId = data[6].toInt()
            val save = data[7].toBoolean()
            val favorite = data[8].toBoolean()

            val lesson = LessonDataInfo(id, titulo, descripcion, imagen, informacion, isExpanded, save, favorite)
            val theme = listTheme.find { it.id == themeId }
            if (theme != null) {
                if (!theme.lecciones.any { it.id == id }) {
                    theme.lecciones.add(lesson)
                }
            }
        }
        input.close()
    }

    data class ThemeDataInfo(
        val id: Int,
        val titulo: String,
        val descripcion: String,
        val icono: String,
        val tema: String,
        val lecciones: MutableList<LessonDataInfo>,
        val color: String
        ) : Serializable

    data class LessonDataInfo(
        val id: Int,
        val titulo: String,
        val descripcion: String,
        val imagen: String,
        val informacion: String,
        var isExpanded: Boolean,
        var save: Boolean,
        var favorite: Boolean
    ) : Serializable
}

