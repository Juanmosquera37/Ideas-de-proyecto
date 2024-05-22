package com.example.mathshark.index.lessonList

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mathshark.databinding.FragmentLessonListBinding
import com.example.mathshark.index.SharedDataViewModel
import com.example.mathshark.adapter.LessonAdapter
import androidx.lifecycle.ViewModelProvider

class LessonListFragment : Fragment() {

    private lateinit var sharedDataViewModel: SharedDataViewModel
    private var _binding: FragmentLessonListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLessonListBinding.inflate(inflater, container, false)

        sharedDataViewModel = ViewModelProvider(requireActivity()).get(SharedDataViewModel::class.java)

        val themeId = arguments?.getInt("theme_id") ?: 0
        val theme = sharedDataViewModel.themes.value?.find { it.id == themeId }
        val lessons = theme?.lecciones ?: emptyList()

        binding.titleTopic.text = theme?.titulo ?: "Título por defecto"
        binding.descriptionLesson.text = theme?.descripcion ?: "Descripción por defecto"

        val adapter = LessonAdapter(requireContext(), ArrayList(lessons))
        binding.lessonList.layoutManager = LinearLayoutManager(requireContext())
        binding.lessonList.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}








