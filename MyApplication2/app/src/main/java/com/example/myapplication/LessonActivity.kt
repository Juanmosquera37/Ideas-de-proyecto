package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityLessonBinding

class LessonActivity : AppCompatActivity() {
    lateinit var binding: ActivityLessonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        val title = intent.getStringExtra("titulo")
        val image = intent.getStringExtra("imagen")
        val info = intent.getStringExtra("informacion")


        binding.titleLesson.text = title
        binding.infoLesson.text = info
        val imageid = resources.getIdentifier(image, "drawable", packageName)
        binding.imageLesson.setImageResource(imageid)

        binding.quizButton.setOnClickListener(){
            val i = Intent(this, QuestionActivity::class.java)
            startActivity(i)
        }
    }
}