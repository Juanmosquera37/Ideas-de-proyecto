package com.example.mathshark.index

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mathshark.R
import java.io.Serializable
import java.util.Scanner

class SharedDataViewModel(application: Application) : AndroidViewModel(application) {

    private val preferences: SharedPreferences =
        application.getSharedPreferences("MathSharkPrefs", Context.MODE_PRIVATE)

    private val _themes = MutableLiveData<List<ThemeDataInfo>>()
    val themes: LiveData<List<ThemeDataInfo>> get() = _themes

    private val _lessons = MutableLiveData<List<LessonDataInfo>>()
    val lessons: LiveData<List<LessonDataInfo>> get() = _lessons

    private val _quizzes = mutableListOf<QuizDataInfo>()

    fun getQuizByLessonId(lessonId: Int): List<QuizDataInfo> {
        return _quizzes.filter { it.idLesson == lessonId }
    }

    var themeList = mutableListOf<ThemeDataInfo>()

    init {
        loadThemeInfo()
        loadLessonInfo()
        loadQuizInfo()
    }


    private fun getCompletedKey(lessonId: Int): String {
        return "Lesson_${lessonId}_Completed"
    }

    fun markLessonCompleted(lessonId: Int) {
        val allLessons = _lessons.value ?: return
        val lesson = allLessons.find { it.id == lessonId } ?: return
        lesson.isUnlocked = true

        val editor = preferences.edit()
        editor.putBoolean(getCompletedKey(lessonId), true)
        editor.apply()
    }


    fun isLessonCompleted(lessonId: Int): Boolean {
        return preferences.getBoolean(getCompletedKey(lessonId), false)
    }

    fun unlockNextLesson(currentLessonId: Int) {
        val allLessons = _lessons.value ?: return
        val currentLessonIndex = allLessons.indexOfFirst { it.id == currentLessonId }
        if (currentLessonIndex != -1 && currentLessonIndex < allLessons.size - 1) {
            val nextLesson = allLessons[currentLessonIndex + 1]
            nextLesson.isUnlocked = true
        }
    }

    private fun loadThemeInfo() {
        val context = getApplication<Application>()
        themeList = mutableListOf()
        val input = Scanner(context.resources.openRawResource(R.raw.themeinfo))
        while (input.hasNextLine()) {
            val data = input.nextLine().split("|")
            val id = data[0].toInt()
            val titulo = data[1]
            val descripcion = data[2]
            val icono = data[3]
            val tema = data[4]
            val color = data[5]

            val theme = ThemeDataInfo(id, titulo, descripcion, icono, tema, mutableListOf(), color)
            themeList.add(theme)
        }
        input.close()

        _themes.value = themeList
    }

    private fun loadLessonInfo() {
        val context = getApplication<Application>()
        val lessonList = mutableListOf<LessonDataInfo>()
        val input = Scanner(context.resources.openRawResource(R.raw.lessoninfo))
        while (input.hasNextLine()) {
            val data = input.nextLine().split("|")
            val id = data[0].toInt()
            val titulo = data[1]
            val descripcion = data[2]
            val imagen = data[3]
            val informacion = data[4]
            val isExpanded = data[5].toBoolean()
            val themeId = data[6].toInt()
            val save = data[7].toBoolean()
            val favorite = data[8].toBoolean()
            val isUnlocked = isLessonCompleted(id) || data[9].toBoolean()
            val image = data[10]
            val video = data[11]

            val lesson = LessonDataInfo(id, titulo,descripcion,imagen,informacion,isExpanded,save,favorite, isUnlocked, image, video)

            lessonList.add(lesson)

            val theme = themeList.find { it.id == themeId }
            theme?.lecciones?.add(lesson)
        }
        input.close()

        _lessons.value = lessonList
    }

    private fun loadQuizInfo() {
        val context = getApplication<Application>()
        val input = Scanner(context.resources.openRawResource(R.raw.quizinfo))
        while (input.hasNextLine()) {
            val data = input.nextLine().split("|")
            val id = data[0].toInt()
            val pregunta = data[1]
            val respuestas = listOf(data[2], data[3], data[4], data[5])
            val respuestaCorrecta = data[6]
            val lessonId = data[7].toInt()

            _quizzes.add(QuizDataInfo(id, pregunta, respuestas, respuestaCorrecta, lessonId))
        }
        input.close()
    }

    data class ThemeDataInfo(
        val id: Int,
        val titulo: String,
        val descripcion: String,
        val icono: String,
        val tema: String,
        val lecciones: MutableList<LessonDataInfo>,
        val color: String
    ) : Serializable

    data class LessonDataInfo(
        val id: Int,
        val titulo: String,
        val descripcion: String,
        val imagen: String,
        val informacion: String,
        var isExpanded: Boolean,
        var save: Boolean,
        var favorite: Boolean,
        var isUnlocked: Boolean,
        val imageUrl: String,
        val video: String,
        val quiz: MutableList<QuizDataInfo> = mutableListOf()
    ) : Serializable

    data class QuizDataInfo(
        val id: Int,
        val pregunta: String,
        val respuesta: List<String>,
        val respuestaCorrecta: String,
        val idLesson: Int
    ) : Serializable
}
