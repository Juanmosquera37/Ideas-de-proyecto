package com.example.mathshark

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.mathshark.databinding.ListLessonBinding

class MyAdapter(private val context: Context, private val dataList: MutableList<MutableList<String>>) :
    BaseAdapter() {

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Any {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ListLessonBinding.inflate(LayoutInflater.from(context), parent, false)

        val lessonData = getItem(position) as MutableList<String>
        val title = lessonData[1]
        val info = lessonData[2]

        binding.header.titleLesson.text = title
        binding.content.infoLesson.text = info

        binding.content.lessonButton.setOnClickListener(){
            val intent = Intent(context, LessonActivity::class.java)
            context.startActivity(intent)
        }


        return binding.root
    }
}