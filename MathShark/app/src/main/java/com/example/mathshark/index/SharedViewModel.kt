package com.example.mathshark.index

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mathshark.R
import java.io.Serializable
import java.util.Scanner

class SharedDataViewModel(application: Application) : AndroidViewModel(application) {

    private val _themes = MutableLiveData<List<ThemeDataInfo>>()
    val themes: LiveData<List<ThemeDataInfo>> get() = _themes

    private val _lessons = MutableLiveData<List<LessonDataInfo>>()
    val lessons: LiveData<List<LessonDataInfo>> get() = _lessons

    private val _quizzes = mutableListOf<QuizDataInfo>()

    private val sharedPreferences = application.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun getQuizByLessonId(lessonId: Int): List<QuizDataInfo> {
        return _quizzes.filter { it.idLesson == lessonId }
    }

    var themeList = mutableListOf<ThemeDataInfo>()

    init {
        loadThemeInfo()
        loadLessonInfo()
        loadQuizInfo()
        restoreSavedState()
    }

    fun getLastFavoriteLesson(): LessonDataInfo? {
        return _lessons.value?.asReversed()?.find { it.favorite }
    }

    fun getLastSavedLesson(): LessonDataInfo? {
        return _lessons.value?.asReversed()?.find { it.save }
    }


    fun markCompletedLessons(currentLessonId: Int) {
        val allLessons = _lessons.value ?: return
        val currentLessonIndex = allLessons.indexOfFirst { it.id == currentLessonId }
        val lesson = allLessons[currentLessonIndex]
        lesson.isCompleted = true
    }

    fun unlockNextLesson(currentLessonId: Int) {
        val allLessons = _lessons.value ?: return
        val currentLessonIndex = allLessons.indexOfFirst { it.id == currentLessonId }
        if (currentLessonIndex != -1 && currentLessonIndex < allLessons.size - 1) {
            val nextLesson = allLessons[currentLessonIndex + 1]
            saveState()
            nextLesson.isUnlocked = true
        }
    }

    fun saveState() {
        val editor = sharedPreferences.edit()
        _lessons.value?.forEach { lesson ->
            editor.putBoolean("lesson_${lesson.id}_unlocked", lesson.isUnlocked)
            editor.putBoolean("lesson_${lesson.id}_completed", lesson.isCompleted)
            editor.putBoolean("lesson_${lesson.id}_saved", lesson.save)
            editor.putBoolean("lesson_${lesson.id}_favorite", lesson.favorite)
        }
        editor.apply()
    }

    private fun restoreSavedState() {
        val allLessons = _lessons.value ?: return
        allLessons.forEach { lesson ->
            val hasUnlocked = sharedPreferences.contains("lesson_${lesson.id}_unlocked")
            val hasCompleted = sharedPreferences.contains("lesson_${lesson.id}_completed")
            val hasSave = sharedPreferences.contains("lesson_${lesson.id}_saved")
            val hasFavorite = sharedPreferences.contains("lesson_${lesson.id}_favorite")

            if (hasUnlocked) {
                lesson.isUnlocked = sharedPreferences.getBoolean("lesson_${lesson.id}_unlocked", false)
            }
            if (hasCompleted) {
                lesson.isCompleted = sharedPreferences.getBoolean("lesson_${lesson.id}_completed", false)
            }
            if (hasSave) {
                lesson.save = sharedPreferences.getBoolean("lesson_${lesson.id}_saved", false)
            }
            if (hasFavorite) {
                lesson.favorite = sharedPreferences.getBoolean("lesson_${lesson.id}_favorite", false)
            }
        }
        _lessons.value = allLessons
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
            val informacion = data[3]
            val isExpanded = data[4].toBoolean()
            val themeId = data[5].toInt()
            val save = data[6].toBoolean()
            val favorite = data[7].toBoolean()
            val isUnlocked = data[8].toBoolean()
            val isCompleted = data[9].toBoolean()
            val image = data[10]
            val video = data[11]

            val lesson = LessonDataInfo(
                id, titulo, descripcion, informacion, isExpanded, themeId, save,
                favorite, isUnlocked, isCompleted, image, video
            )

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
        val id: Int = 0,
        val titulo: String = "",
        val descripcion: String = "",
        val icono: String = "",
        val tema: String = "",
        val lecciones: MutableList<LessonDataInfo> = mutableListOf(),
        val color: String = ""
    ) : Serializable

    data class LessonDataInfo(
        val id: Int,
        val titulo: String,
        val descripcion: String,
        val informacion: String,
        var isExpanded: Boolean,
        val themeId: Int,
        var save: Boolean,
        var favorite: Boolean,
        var isUnlocked: Boolean,
        var isCompleted: Boolean,
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
