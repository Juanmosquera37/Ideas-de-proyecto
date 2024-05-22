package com.example.mathshark.index

import android.app.Application
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

    var themeList = mutableListOf<ThemeDataInfo>()

    init {
        loadThemeInfo()
        loadLessonInfo()
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

            val lesson = LessonDataInfo(id, titulo, descripcion, imagen, informacion, isExpanded, save, favorite)
            lessonList.add(lesson)

            val theme = themeList.find { it.id == themeId }
            theme?.lecciones?.add(lesson)
        }
        input.close()

        _lessons.value = lessonList
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
        var favorite: Boolean
    ) : Serializable
}