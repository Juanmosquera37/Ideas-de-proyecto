package com.example.mathshark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mathshark.databinding.ActivityLessonListBinding
import java.util.Scanner

class LessonListActivity : AppCompatActivity() {

    lateinit var binding: ActivityLessonListBinding
    val lessonInfo = mutableListOf<MutableList<String>>()

    val topics = arrayListOf<String>("Ecuacion Lineal", "Ecuacion Cuadratica")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.titleTopic.text = "Ecuaciones"

        readLessonInfo()

        val adapter = MyAdapter(this, lessonInfo)
        binding.lessonList.adapter = adapter
    }




    fun readLessonInfo() {
        val input = Scanner(resources.openRawResource(R.raw.lessoninfo))
        var count = 0
        while (input.hasNextLine()) {
            val data = input.nextLine().split("|")
            lessonInfo.add(data.toMutableList())
            count += 1
        }
        input.close()
    }

}