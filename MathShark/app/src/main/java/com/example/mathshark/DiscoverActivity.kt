package com.example.mathshark

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.mathshark.databinding.ActivityDiscoverBinding
import com.example.mathshark.lessonList.LessonListFragment
import com.example.mathshark.ui.discover.DiscoverFragment.LessonDataInfo

class DiscoverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiscoverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
		binding = ActivityDiscoverBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val lessons = intent.getSerializableExtra("lessons") as? List<LessonDataInfo> // Obteniendo el arreglo

        if (lessons != null) {
            val bundle = Bundle()
            bundle.putString("title", title)
            bundle.putString("description", description)
            bundle.putSerializable("lessons", ArrayList(lessons))

            val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            val lessonListFragment = LessonListFragment().apply {
                arguments = bundle
            }

            fragmentTransaction.replace(R.id.fragment_discover, lessonListFragment)
            fragmentTransaction.commit()
        } else {
            throw IllegalStateException("No lessons data found to pass to fragment")
        }
    }
}