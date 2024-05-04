package com.example.mathshark.lesson

import android.content.res.ColorStateList
import android.graphics.Color
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
        private const val ARG_FAVORITE = "favorito"
        private const val ARG_SAVE= "guardado"

        fun newInstance(title: String?, image: String?, information: String?, favorite: Boolean, save: Boolean): LessonFragment {
            val fragment = LessonFragment()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            args.putString(ARG_IMAGE, image)
            args.putString(ARG_INFORMATION, information)
            args.putBoolean(ARG_FAVORITE, favorite)
            args.putBoolean(ARG_SAVE, save)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
		_binding = FragmentLessonBinding.inflate(inflater, container, false)
        val root = binding.root

        val title = arguments?.getString(ARG_TITLE)
        val image = arguments?.getString(ARG_IMAGE)
        val info = arguments?.getString(ARG_INFORMATION)
        var favorite = arguments?.getBoolean(ARG_FAVORITE)
        var save = arguments?.getBoolean(ARG_SAVE)


        binding.titleLesson.text = title
        binding.infoLesson.text = info
        val imageId = resources.getIdentifier(image, "drawable", requireContext().packageName)
        binding.imageLesson.setImageResource(imageId)
		
		binding.favoriteIcon.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.favoriteIcon.buttonTintList = ColorStateList.valueOf(Color.parseColor("#FF0000"))
                favorite = true
            } else {
                binding.favoriteIcon.buttonTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                favorite = false
            }
        }

        binding.SavedIcon.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.SavedIcon.buttonTintList = ColorStateList.valueOf(Color.parseColor("#0000FF"))
                save = true
            } else {
                binding.SavedIcon.buttonTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                save = false
            }
        }

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
	
	override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}