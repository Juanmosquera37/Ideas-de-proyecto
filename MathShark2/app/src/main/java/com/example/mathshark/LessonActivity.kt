package com.example.mathshark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mathshark.databinding.ActivityLessonBinding

class LessonActivity : AppCompatActivity() {

    lateinit var binding: ActivityLessonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val topicName = intent.getStringExtra("Param_topic")
        binding.titleLesson.text = topicName

        val imageId = resources.getIdentifier(topicName, "drawable", packageName)
        binding.imageLesson.setImageResource(imageId)

        binding.titleQuiz.text = "Quiz"
        binding.infoQuiz.text = "Acontinuacion podra realizar un quiz en el cual, podra reforzar el conocimiento adquirido en la leccion"
    }
}