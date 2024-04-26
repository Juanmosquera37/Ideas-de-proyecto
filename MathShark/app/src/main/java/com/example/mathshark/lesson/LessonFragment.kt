package com.example.mathshark.lesson

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.example.mathshark.R
import com.example.mathshark.databinding.FragmentLessonBinding
import com.example.mathshark.quiz.QuizFragment

class LessonFragment : Fragment() {

    private var _binding: FragmentLessonBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_IMAGE = "image"
        private const val ARG_INFORMATION = "information"
        fun newInstance(title: String?,image: String?, information: String?): LessonFragment {
            val fragment = LessonFragment()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            args.putString(ARG_IMAGE, image)
            args.putString(ARG_INFORMATION, information)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var viewModel: LessonViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val lessonViewModel =
        ViewModelProvider(this).get(LessonViewModel::class.java)
        _binding = FragmentLessonBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val title = arguments?.getString(ARG_TITLE)
        val image = arguments?.getString(ARG_IMAGE)
        val info = arguments?.getString(ARG_INFORMATION)


        binding.titleLesson.text = title
        binding.infoLesson.text = info
        val imageId = resources.getIdentifier(image, "drawable", requireContext().packageName)
        binding.imageLesson.setImageResource(imageId)

        binding.quizButton.setOnClickListener(){
            val fragmentManager = (requireActivity() as FragmentActivity).supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val newFragment = QuizFragment()

            fragmentTransaction.replace(R.id.fragment_discover, newFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return root
    }

}