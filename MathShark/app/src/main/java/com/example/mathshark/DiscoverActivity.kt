package com.example.mathshark

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mathshark.databinding.ActivityDiscoverBinding
import com.example.mathshark.lessonList.LessonListFragment

class DiscoverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiscoverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDiscoverBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        val title = intent.getStringExtra("Titulo")
        val description = intent.getStringExtra("Descripcion")

        val fragment = LessonListFragment.newInstance(title, description)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_discover, fragment)
            .commit()
    }
}