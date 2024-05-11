package com.example.mathshark.index.DailyQuestion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mathshark.R
import java.util.*

class DailyQuestionFragment : Fragment() {

    private lateinit var titleQuestion: TextView
    private lateinit var groupAnswer: RadioGroup
    private lateinit var answer1: RadioButton
    private lateinit var answer2: RadioButton
    private lateinit var answer3: RadioButton
    private lateinit var answer4: RadioButton
    private lateinit var buttonSubmit: Button

    private val preguntas = arrayOf(
        "2 + 2 =",
        "5 + 3 =",
        "10 + 7 =",
        "8 + 6 =",
        "7 - 3 =",
        "12 - 5 =",
        "15 - 8 =",
        "9 - 6 =",
        "2 * 3 =",
        "4 * 5 =",
        "7 * 8 =",
        "9 * 2 =",
        "6 / 2 =",
        "9 / 3 =",
        "15 / 5 =",
        "20 / 4 ="
    )

    private val respuestas = arrayOf(
        "4", "8", "17", "14",
        "4", "7", "7", "3",
        "6", "20", "56", "18",
        "3", "3", "3", "5"
    )

    private var preguntaActual = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_daily_question, container, false)

        titleQuestion = view.findViewById(R.id.titleQuestion)
        groupAnswer = view.findViewById(R.id.groupAnswer)
        answer1 = view.findViewById(R.id.answer1)
        answer2 = view.findViewById(R.id.answer2)
        answer3 = view.findViewById(R.id.answer3)
        answer4 = view.findViewById(R.id.answer4)
        buttonSubmit = view.findViewById(R.id.buttonSubmit)

        mostrarNuevaPregunta()

        buttonSubmit.setOnClickListener {

            // Aquí verificamos si la respuesta seleccionada es correcta
            val respuestaSeleccionada = when (groupAnswer.checkedRadioButtonId) {
                R.id.answer1 -> answer1.text.toString()
                R.id.answer2 -> answer2.text.toString()
                R.id.answer3 -> answer3.text.toString()
                R.id.answer4 -> answer4.text.toString()
                else -> ""
            }

            val respuestaCorrecta = respuestas[preguntaActual]

            if (respuestaSeleccionada == respuestaCorrecta) {
                titleQuestion.text = "¡Respuesta correcta!"
            } else {
                titleQuestion.text = "Respuesta incorrecta"
            }

            disableAnswers()
        }

        return view
    }

    private fun mostrarNuevaPregunta() {
        titleQuestion.text = preguntas[preguntaActual]

        // Seleccionamos un número aleatorio entre 1 y 4 para determinar en qué opción colocar la respuesta correcta
        val random = Random()
        val opciones = mutableListOf("0", "1", "2", "3")
        opciones.shuffle()

        answer1.text = respuestas[preguntaActual * 4 + opciones[0].toInt()]
        answer2.text = respuestas[preguntaActual * 4 + opciones[1].toInt()]
        answer3.text = respuestas[preguntaActual * 4 + opciones[2].toInt()]
        answer4.text = respuestas[preguntaActual * 4 + opciones[3].toInt()]

        enableAnswers()
    }

    private fun disableAnswers() {
        answer1.isEnabled = false
        answer2.isEnabled = false
        answer3.isEnabled = false
        answer4.isEnabled = false
        buttonSubmit.isEnabled = false
    }

    private fun enableAnswers() {
        answer1.isEnabled = true
        answer2.isEnabled = true
        answer3.isEnabled = true
        answer4.isEnabled = true
        buttonSubmit.isEnabled = true
    }
}
