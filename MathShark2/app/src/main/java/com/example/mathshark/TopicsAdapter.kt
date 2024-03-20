package com.example.mathshark

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

val infoTopics = arrayListOf<String>(
    "En esta unidad podras aprender a como resolver la ecuaciones lineales",
    "En esta unidad podras aprender a como resolver la ecuaciones cuadraticas"
)

class TopicsAdapter(val context: Activity, val layout: Int, val data: ArrayList<String>)
    : ArrayAdapter<String>(context, layout, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = context.layoutInflater.inflate(layout, null)
        val name = view.findViewById<TextView>(R.id.tileTopic)
        name.text = data[position]
        val info = view.findViewById<TextView>(R.id.infoTopic)
        info.text = infoTopics[position]
        val imageId = context.resources.getIdentifier(data[position].lowercase().replace("\\s".toRegex(), ""), "drawable", context.packageName)
        val image = view.findViewById<ImageView>(R.id.imageTopic)
        image.setImageResource(imageId)
        return view
    }
}