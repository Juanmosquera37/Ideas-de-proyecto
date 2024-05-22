package com.example.mathshark.index

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mathshark.R
import com.example.mathshark.databinding.ActivityMainBinding
import java.io.Serializable
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val lessonInfo: ArrayList<LessonDataInfo> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        readLessonInfo()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.navigation_user
            )
        )



        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun readLessonInfo() {
        val input = Scanner(resources.openRawResource(R.raw.lessoninfo))
        while (input.hasNextLine()) {
            val data = input.nextLine().split("|")

            val id = data[0].toInt()
            val titulo = data[1]
            val descripcion = data[2]
            val imagen = data[3]
            val informacion = data[4]
            val expandir = data[5].toBoolean()

            val info = LessonDataInfo(id, titulo, descripcion, imagen, informacion, expandir)
            lessonInfo.add(info)
        }
        input.close()
    }

    data class LessonDataInfo(val id: Int, val titulo: String, val descripcion: String, val imagen: String, val informacion: String, var isExpanded: Boolean) :
        Serializable

}