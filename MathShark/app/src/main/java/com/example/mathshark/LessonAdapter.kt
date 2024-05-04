import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mathshark.R
import com.example.mathshark.databinding.ListExpandableBinding
import com.example.mathshark.lesson.LessonFragment
import com.example.mathshark.ui.discover.DiscoverFragment.LessonDataInfo

class LessonAdapter(
    private val context: Context,
    private val datalist: ArrayList<LessonDataInfo>
) : RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {

    inner class LessonViewHolder(val binding: ListExpandableBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val binding = ListExpandableBinding.inflate(LayoutInflater.from(context), parent, false)
        return LessonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val currentLesson = datalist[position]

        holder.binding.titleLesson.text = currentLesson.titulo
        holder.binding.infoLesson.text = currentLesson.descripcion

        if (currentLesson.isExpanded) {
            holder.binding.expandableLayout.visibility = View.VISIBLE

            holder.binding.lessonButton.setOnClickListener {
                val fragmentManager = (context as FragmentActivity).supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                val fragment = LessonFragment.newInstance(currentLesson.titulo, currentLesson.imagen, currentLesson.informacion, currentLesson.favorite, currentLesson.save)

                fragmentTransaction.replace(R.id.fragment_discover, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        } else {
            holder.binding.expandableLayout.visibility = View.GONE
        }

        holder.binding.root.setOnClickListener {
            currentLesson.isExpanded = !currentLesson.isExpanded
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}