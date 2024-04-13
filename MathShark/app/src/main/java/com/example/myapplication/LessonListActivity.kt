package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityLessonListBinding
import java.util.Scanner

class LessonListActivity : AppCompatActivity() {

    lateinit var binding: ActivityLessonListBinding
    val lessonInfo: ArrayList<Lesson> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonListBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        val title = intent.getStringExtra("Titulo")
        val description = intent.getStringExtra("Descripcion")

        binding.titleTopic.text = title
        binding.descriptionLesson.text = description

        readLessonInfo()

        val adapter = LessonAdapter(this, lessonInfo)
        binding.lessonList.adapter = adapter
        binding.lessonList.layoutManager = LinearLayoutManager(this)
    }

    fun readLessonInfo() {
        val input = Scanner(resources.openRawResource(R.raw.lessonnfo))
            while (input.hasNextLine()) {
                val data = input.nextLine().split("|")

                val id = data[0].toInt()
                val titulo = data[1]
                val descripcion = data[2]
                val imagen = data[3]
                val informacion = data[4]

                val lesson = Lesson(id, titulo, descripcion, imagen, informacion)
                lessonInfo.add(lesson)
            }
                input.close()
        }

}


data class Lesson(val id: Int, val titulo: String, val descripcion: String, val imagen: String, val informacion: String)