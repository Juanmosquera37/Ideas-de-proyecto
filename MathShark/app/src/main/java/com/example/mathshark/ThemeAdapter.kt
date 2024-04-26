package com.example.mathshark

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mathshark.databinding.ListThemeBinding
import com.example.mathshark.ui.discover.DiscoverFragment

class ThemeAdapter(private val context: Context, private val dataList: ArrayList<DiscoverFragment.ThemeDataInfo>) :
    RecyclerView.Adapter<ThemeAdapter.ThemeViewHolder>() {

    class ThemeViewHolder(val binding: ListThemeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeViewHolder {
        val binding = ListThemeBinding.inflate(LayoutInflater.from(context), parent, false)
        return ThemeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ThemeViewHolder, position: Int) {
        val currentTheme = dataList[position]

        holder.binding.themeButton.text = currentTheme.titulo

        val iconResourceId = context.resources.getIdentifier(currentTheme.icono, "drawable", context.packageName)
        holder.binding.themeButton.setIconResource(iconResourceId)

        holder.binding.themeButton.setOnClickListener {
            val intent = Intent(context, DiscoverActivity::class.java)
            intent.putExtra("Titulo", currentTheme.titulo)
            intent.putExtra("Descripcion", currentTheme.descripcion)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}



