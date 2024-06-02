package com.example.mathshark.index

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mathshark.R
import com.example.mathshark.databinding.ActivityHomeBinding
import com.example.mathshark.index.DailyQuestion.DailyQuestionFragment



class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        val fragment = DailyQuestionFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_Home, fragment)
            .commit()



    }
}
