package com.example.mathshark.lessonList

import LessonAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mathshark.R
import com.example.mathshark.databinding.FragmentLessonListBinding
import java.io.Serializable
import java.util.Scanner


class LessonListFragment : Fragment() {

    private var _binding: FragmentLessonListBinding? = null
    private val binding get() = _binding!!

    val lessonInfo: ArrayList<LessonDataInfo> = ArrayList()

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_DESCRIPTION = "description"

        fun newInstance(title: String?, description: String?): LessonListFragment {
            val fragment = LessonListFragment()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            args.putString(ARG_DESCRIPTION, description)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val lessonListViewModel =
            ViewModelProvider(this).get(LessonListViewModel::class.java)
        _binding = FragmentLessonListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val title = arguments?.getString(ARG_TITLE)
        val description = arguments?.getString(ARG_DESCRIPTION)


        binding.titleTopic.text = title
        binding.descriptionLesson.text = description

        readLessonInfo()

        val adapter = LessonAdapter(requireContext(), lessonInfo)
        binding.lessonList.adapter = adapter
        binding.lessonList.layoutManager = LinearLayoutManager(requireContext())


        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
            val expandir = data[5].toBoolean()

            val lesson = LessonDataInfo(id, titulo, descripcion, imagen, informacion, expandir)
            lessonInfo.add(lesson)
        }
        input.close()
    }

    data class LessonDataInfo(val id: Int, val titulo: String, val descripcion: String, val imagen: String, val informacion: String, var isExpanded: Boolean) :
        Serializable

}

