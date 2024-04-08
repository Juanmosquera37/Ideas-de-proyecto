package com.example.mathshark

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mathshark.databinding.ActivityLessonBinding

class LessonActivity : AppCompatActivity() {

    lateinit var binding: ActivityLessonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.quizButton.setOnClickListener(){
            val i = Intent(this, QuestionActivity::class.java)
            startActivity(i)
        }
    }
}