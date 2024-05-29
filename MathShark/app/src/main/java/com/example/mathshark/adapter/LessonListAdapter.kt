package com.example.mathshark.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mathshark.R
import com.example.mathshark.databinding.ListLessonBinding
import com.example.mathshark.index.lesson.LessonFragment
import com.example.mathshark.index.SharedDataViewModel

class LessonListAdapter(
    private val context: Context,
    private val dataList: ArrayList<SharedDataViewModel.LessonDataInfo>,
    private val viewModel: SharedDataViewModel
) : RecyclerView.Adapter<LessonListAdapter.LessonViewHolder>() {

    inner class LessonViewHolder(val binding: ListLessonBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val binding = ListLessonBinding.inflate(LayoutInflater.from(context), parent, false)
        return LessonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val currentLesson = dataList[position]

        holder.binding.titleLesson.text = currentLesson.titulo
        holder.binding.infoLesson.text = currentLesson.descripcion

        val isCompleted = currentLesson.isCompleted
        val buttonColor = when {
            isCompleted -> Color.parseColor("#00FF00")
            currentLesson.isUnlocked -> Color.parseColor("#2965CC")
            else -> Color.parseColor("#BFBFBF")
        }

        holder.binding.lessonButton.backgroundTintList = ColorStateList.valueOf(buttonColor)


        holder.binding.expandableLayout.visibility =
            if (currentLesson.isExpanded) View.VISIBLE else View.GONE

        holder.binding.root.setOnClickListener {
            currentLesson.isExpanded = !currentLesson.isExpanded
            notifyItemChanged(position)
        }

        holder.binding.lessonButton.setOnClickListener {
            if (currentLesson.isUnlocked) {
                val fragmentManager = (context as FragmentActivity).supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                val fragment = LessonFragment.newInstance(currentLesson.id)
                fragmentTransaction.replace(R.id.fragment_discover, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            } else {
                Toast.makeText(context, "Lección bloqueada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}



