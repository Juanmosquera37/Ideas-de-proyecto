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

    private val preguntasYRespuestas = arrayOf(
        "2 + 2 =" to "4",
        "5 + 3 =" to "8",
        "10 + 7 =" to "17",
        "8 + 6 =" to "14",
        "7 - 3 =" to "4",
        "12 - 5 =" to "7",
        "15 - 8 =" to "7",
        "9 - 6 =" to "3",
        "2 * 3 =" to "6",
        "4 * 5 =" to "20",
        "7 * 8 =" to "56",
        "9 * 2 =" to "18",
        "6 / 2 =" to "3",
        "9 / 3 =" to "3",
        "15 / 5 =" to "3",
        "20 / 4 =" to "5"
    )

    private var preguntaActual = 0
    private var respuestaCorrecta = ""

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
        val random = Random()
        preguntaActual = random.nextInt(preguntasYRespuestas.size)
        val (pregunta, respuesta) = preguntasYRespuestas[preguntaActual]

        titleQuestion.text = pregunta
        respuestaCorrecta = respuesta

        // Generamos opciones aleatorias junto con la respuesta correcta
        val opciones = mutableListOf(respuesta)
        while (opciones.size < 4) {
            val opcionAleatoria = (0..100).random().toString()
            if (opcionAleatoria !in opciones) {
                opciones.add(opcionAleatoria)
            }
        }

        opciones.shuffle()

        answer1.text = opciones[0]
        answer2.text = opciones[1]
        answer3.text = opciones[2]
        answer4.text = opciones[3]

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
        groupAnswer.clearCheck()
        answer1.isEnabled = true
        answer2.isEnabled = true
        answer3.isEnabled = true
        answer4.isEnabled = true
        buttonSubmit.isEnabled = true
    }
}
