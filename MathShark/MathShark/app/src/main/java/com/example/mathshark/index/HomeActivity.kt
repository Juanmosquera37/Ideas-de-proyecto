package com.example.mathshark.index

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentTransaction
import com.example.mathshark.R
import com.example.mathshark.databinding.ActivityHomeBinding
import com.example.mathshark.index.DailyQuestion.DailyQuestionFragment
import com.example.mathshark.index.lessonList.LessonListFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        val themeId = intent.getIntExtra("id", 0)

        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        val DailyQuestionFragment = DailyQuestionFragment().apply {
            arguments = Bundle().apply {
                putInt("theme_id", themeId)
            }
        }
        fragmentTransaction.replace(R.id.fragment_Home, DailyQuestionFragment)
        fragmentTransaction.commit()
    }
}
