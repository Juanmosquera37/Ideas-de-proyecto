package com.example.myapplication
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
class DailyQuestion : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.daily_question)
    }
}