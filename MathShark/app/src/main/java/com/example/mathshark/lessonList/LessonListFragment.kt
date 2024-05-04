package com.example.mathshark.lessonList

import LessonAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mathshark.databinding.FragmentLessonListBinding
import com.example.mathshark.ui.discover.DiscoverFragment.LessonDataInfo

class LessonListFragment : Fragment() {

    private var _binding: FragmentLessonListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLessonListBinding.inflate(inflater, container, false)
        val root = binding.root

        val title = arguments?.getString("title")
        val description = arguments?.getString("description")
        val lessons = arguments?.getSerializable("lessons") as? List<LessonDataInfo>

        binding.titleTopic.text = title ?: "Título por defecto"
        binding.descriptionLesson.text = description ?: "Descripción por defecto"

        if (lessons != null) {
            val adapter = LessonAdapter(requireContext(), ArrayList(lessons))
            binding.lessonList.layoutManager = LinearLayoutManager(requireContext())
            binding.lessonList.adapter = adapter
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
	
}

