package com.example.mathshark.index.lesson

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mathshark.databinding.FragmentLessonBinding
import androidx.lifecycle.ViewModelProvider
import android.content.res.ColorStateList
import android.graphics.Color
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.mathshark.R
import com.example.mathshark.index.SharedDataViewModel
import com.example.mathshark.index.quiz.QuizFragment
import com.squareup.picasso.Picasso

class LessonFragment : Fragment() {

    private var _binding: FragmentLessonBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedDataViewModel: SharedDataViewModel

    companion object {
        private const val ARG_LESSON_ID = "lesson_id"

        fun newInstance(lessonId: Int): LessonFragment {
            val fragment = LessonFragment()
            val args = Bundle()
            args.putInt(ARG_LESSON_ID, lessonId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLessonBinding.inflate(inflater, container, false)

        sharedDataViewModel = ViewModelProvider(requireActivity()).get(SharedDataViewModel::class.java)

        val lessonId = arguments?.getInt(ARG_LESSON_ID) ?: 0
        val lesson = sharedDataViewModel.lessons.value?.find { it.id == lessonId }

        binding.titleLesson.text = lesson?.titulo ?: "Título no encontrado"
        binding.infoLesson.text = lesson?.informacion ?: "informacion no encontrada"

        val imageUrl = lesson?.imageUrl ?: ""
        Picasso.get().load(imageUrl).into(binding.imageLesson)

        val videoUrl = lesson?.video ?: ""
        binding.videoLesson.loadData(videoUrl, "text/html","utf-8")
        binding.videoLesson.settings.javaScriptEnabled = true
        binding.videoLesson.webViewClient = WebViewClient()

        binding.favoriteIcon.isChecked = lesson?.favorite ?: false
        binding.favoriteIcon.setOnCheckedChangeListener { _, isChecked ->
            val color = if (isChecked) "#FF0000" else "#FFFFFF"
            binding.favoriteIcon.buttonTintList = ColorStateList.valueOf(Color.parseColor(color))
        }

        binding.SavedIcon.isChecked = lesson?.save ?: false
        binding.SavedIcon.setOnCheckedChangeListener { _, isChecked ->
            val color = if (isChecked) "#0000FF" else "#FFFFFF"
            binding.SavedIcon.buttonTintList = ColorStateList.valueOf(Color.parseColor(color))
        }

        binding.quizButton.setOnClickListener {
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            val quizFragment = QuizFragment.newInstance(lessonId)
            fragmentTransaction.replace(R.id.fragment_discover, quizFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

