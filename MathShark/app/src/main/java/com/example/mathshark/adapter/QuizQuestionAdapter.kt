package com.example.mathshark.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.example.mathshark.databinding.ListQuestionBinding
import com.example.mathshark.index.SharedDataViewModel

class QuizAdapter(
    private val context: Context,
    private val dataList: List<SharedDataViewModel.QuizDataInfo>,
    private val onSelectionChanged: (Boolean) -> Unit
) : RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    private val selectedAnswers = mutableMapOf<Int, String>()

    inner class QuizViewHolder(val binding: ListQuestionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val binding = ListQuestionBinding.inflate(LayoutInflater.from(context), parent, false)
        return QuizViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        val currentQuiz = dataList[position]
        holder.binding.titleQuestion.text = currentQuiz.pregunta

        val radioGroup = holder.binding.radioGroup
        radioGroup.removeAllViews()

        currentQuiz.respuesta.forEach { answer ->
            val radioButton = RadioButton(context).apply {
                text = answer
                setTextColor(Color.WHITE)
            }
            radioGroup.addView(radioButton)
        }

        selectedAnswers[position]?.let { selectedAnswer ->
            val radioButton = radioGroup.children
                .filterIsInstance<RadioButton>()
                .firstOrNull { it.text == selectedAnswer }
            radioButton?.isChecked = true
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton = group.findViewById<RadioButton>(checkedId)
            selectedAnswers[position] = selectedRadioButton.text.toString()
            onSelectionChanged(selectedAnswers.size == dataList.size)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun validateAnswers(): Boolean {
        if (selectedAnswers.size != dataList.size) {
            return false
        }

        for ((position, selectedAnswer) in selectedAnswers) {
            val correctAnswer = dataList[position].respuestaCorrecta
            if (selectedAnswer != correctAnswer) {
                return false
            }
        }
        return true
    }

    fun resetSelections() {
        selectedAnswers.clear()
        notifyDataSetChanged()
    }
}



