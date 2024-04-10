package com.example.mathshark

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mathshark.databinding.ListLessonBinding
import com.robertlevonyan.views.expandable.ExpandableIconStyles
import kotlin.collections.ArrayList

class LessonAdapter(private val context: Context, private val dataList: ArrayList<Lesson>) :
    RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {

    class LessonViewHolder(val binding: ListLessonBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val binding = ListLessonBinding.inflate(LayoutInflater.from(context), parent, false)
        return LessonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val currentLesson = dataList[position]

        holder.binding.header.titleLesson.text = currentLesson.titulo
        holder.binding.content.infoLesson.text = currentLesson.descripcion

        holder.binding.content.lessonButton.setOnClickListener {
            val intent = Intent(context, LessonActivity::class.java)
            intent.putExtra("titulo", currentLesson.titulo)
            intent.putExtra("imagen", currentLesson.imagen)
            intent.putExtra("informacion", currentLesson.informacion)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}