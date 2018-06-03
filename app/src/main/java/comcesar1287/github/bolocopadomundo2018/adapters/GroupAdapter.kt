package comcesar1287.github.bolocopadomundo2018.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.woxthebox.draglistview.DragItemAdapter
import comcesar1287.github.bolocopadomundo2018.R
import comcesar1287.github.bolocopadomundo2018.models.GroupRecyclerView
import kotlinx.android.synthetic.main.item_groups.view.*

class GroupAdapter(private val list: List<Pair<Long, GroupRecyclerView>>, private val grabHandleId: Int,
                   private val dragOnLongPress: Boolean): DragItemAdapter<List<Pair<Int, String>>, GroupAdapter.ViewHolder>() {

    override fun getUniqueItemId(position: Int): Long {
        return list[position].first
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_groups, parent, false)
        return ViewHolder(view, grabHandleId, dragOnLongPress)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.bind(list[position])
    }

    class ViewHolder(itemView: View, grabHandleId: Int, dragOnLongPress: Boolean) : DragItemAdapter.ViewHolder(itemView,
            grabHandleId, dragOnLongPress) {
        fun bind(pair: Pair<Long, GroupRecyclerView>) = with(itemView) {
            val groupRecyclerView = pair.second

            groupRecyclerView.teamIcon?.let {
                itemView.teamIcon.setImageResource(it)
            }
            itemView.teamName.text = groupRecyclerView.teamName
        }
    }
}