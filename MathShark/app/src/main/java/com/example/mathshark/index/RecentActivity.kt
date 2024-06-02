package com.example.mathshark.index

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mathshark.R
import com.example.mathshark.databinding.ActivityRecentBinding
import com.example.mathshark.index.DailyQuestion.DailyQuestionFragment
import com.example.mathshark.index.lesson.LessonFragment

class RecentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecentBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        val lessonId = intent.getIntExtra("LESSON_ID", -1)
        if (lessonId != -1) {
            loadLessonFragment(lessonId)
        }
    }

    private fun loadLessonFragment(lessonId: Int) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val lessonFragment = LessonFragment.newInstance(lessonId)
        fragmentTransaction.replace(R.id.fragment_recent, lessonFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }



}