package com.example.mathshark

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mathshark.databinding.ActivityLessonListBinding

class LessonListActivity : AppCompatActivity() {

    lateinit var binding: ActivityLessonListBinding
    lateinit var adapter: TopicsAdapter

    val topics = arrayListOf<String>("Ecuacion Lineal", "Ecuacion Cuadratica")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.titleTopic.text = "Ecuaciones"

        binding.topicList.setOnItemClickListener { parent, view, position, id ->
            val i = Intent(this, LessonActivity::class.java)
            i.putExtra("Param_topic", topics[position].lowercase().replace("\\s".toRegex(), ""),)
            startActivity(i)
        }

        adapter = TopicsAdapter(this, R.layout.list_topics, topics)
        binding.topicList.adapter = adapter
    }




    fun topicList (topicName: String){
        topics.add(topicName)
        adapter.notifyDataSetChanged()
    }

}