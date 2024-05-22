package com.example.mathshark.index

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.mathshark.R
import com.example.mathshark.databinding.ActivityDiscoverBinding
import com.example.mathshark.index.lessonList.LessonListFragment

class LessonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiscoverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiscoverBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        val themeId = intent.getIntExtra("id", 0)

        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        val lessonListFragment = LessonListFragment().apply {
            arguments = Bundle().apply {
                putInt("theme_id", themeId)
            }
        }
        fragmentTransaction.replace(R.id.fragment_discover, lessonListFragment)
        fragmentTransaction.commit()
    }
}

