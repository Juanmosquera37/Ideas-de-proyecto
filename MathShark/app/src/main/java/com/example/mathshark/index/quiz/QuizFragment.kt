package com.example.mathshark.index.quiz

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mathshark.adapter.QuizAdapter
import com.example.mathshark.databinding.FragmentQuizBinding
import com.example.mathshark.index.SharedDataViewModel
import com.example.mathshark.index.SharedDataViewModel.LessonDataInfo

class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedDataViewModel: SharedDataViewModel
    private lateinit var quizAdapter: QuizAdapter
    private lateinit var currentLesson: LessonDataInfo

    companion object {
        private const val ARG_LESSON_ID = "lesson_id"

        fun newInstance(lessonId: Int): QuizFragment {
            val fragment = QuizFragment()
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
        _binding = FragmentQuizBinding.inflate(inflater, container, false)

        sharedDataViewModel = ViewModelProvider(requireActivity()).get(SharedDataViewModel::class.java)

        val lessonId = arguments?.getInt(ARG_LESSON_ID) ?: 0
        currentLesson = sharedDataViewModel.lessons.value?.find { it.id == lessonId } ?: LessonDataInfo(0, "", "", "", "", false, false, false, false, "", "")

        val quizData = sharedDataViewModel.getQuizByLessonId(lessonId)

        setupRecyclerView(quizData)
        setupQuizButton()

        return binding.root
    }

    private fun setupRecyclerView(quizData: List<SharedDataViewModel.QuizDataInfo>) {
        binding.quizRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        quizAdapter = QuizAdapter(requireContext(), quizData) { allAnswered ->
            binding.quizButton.isEnabled = allAnswered
        }

        binding.quizRecyclerView.adapter = quizAdapter
        binding.quizButton.isEnabled = false  // Deshabilitar el botón inicialmente
    }

    private fun setupQuizButton() {
        binding.quizButton.setOnClickListener {
            if (!binding.quizButton.isEnabled) {
                Toast.makeText(requireContext(), "Responda todas las preguntas", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (quizAdapter.validateAnswers()) {
                Toast.makeText(requireContext(), "¡Superaste el cuestionario!", Toast.LENGTH_SHORT).show()

                sharedDataViewModel.unlockNextLesson(currentLesson.id)
                sharedDataViewModel.markLessonCompleted(currentLesson.id)

                // Desbloquear la siguiente lección si existe
                val allLessons = sharedDataViewModel.lessons.value ?: emptyList()
                val currentLessonIndex = allLessons.indexOf(currentLesson)
                if (currentLessonIndex != -1 && currentLessonIndex < allLessons.size - 1) {
                    val nextLesson = allLessons[currentLessonIndex + 1]
                    nextLesson.isUnlocked = true
                    sharedDataViewModel.markLessonCompleted(nextLesson.id)
                }

                requireActivity().onBackPressed()
            } else {
                Toast.makeText(requireContext(), "Vuelve a intentarlo", Toast.LENGTH_SHORT).show()
                quizAdapter.resetSelections()  // Reiniciar las selecciones
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
