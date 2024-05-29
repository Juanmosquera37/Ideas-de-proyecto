import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mathshark.databinding.ListThemeBinding
import com.example.mathshark.index.LessonActivity
import com.example.mathshark.index.SharedDataViewModel

class ThemeListAdapter(
    private val context: Context,
    private var dataList: List<SharedDataViewModel.ThemeDataInfo>
) : RecyclerView.Adapter<ThemeListAdapter.ThemeViewHolder>() {

    private var originalData = dataList.toList()

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

        val color = Color.parseColor(currentTheme.color)
        holder.binding.themeButton.backgroundTintList = ColorStateList.valueOf(color)

        holder.binding.themeButton.setOnClickListener {
            val intent = Intent(context, LessonActivity::class.java)
            intent.putExtra("id", currentTheme.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateData(newData: List<SharedDataViewModel.ThemeDataInfo>) {
        originalData = newData.toList()
        dataList = newData
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        dataList = originalData.filter { it.titulo.contains(query, ignoreCase = true) }
        notifyDataSetChanged()
    }
}








