package comcesar1287.github.bolocopadomundo2018.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.woxthebox.draglistview.DragItemAdapter
import comcesar1287.github.bolocopadomundo2018.R
import comcesar1287.github.bolocopadomundo2018.models.GroupRecyclerView
import kotlinx.android.synthetic.main.item_groups.view.*

class GroupAdapter(list: MutableList<Pair<Long, GroupRecyclerView>>, private val grabHandleId: Int,
                   private val dragOnLongPress: Boolean): DragItemAdapter<Pair<Long, GroupRecyclerView>, GroupAdapter.ViewHolder>() {

    init {
        setHasStableIds(true)
        itemList = list
    }

    override fun getUniqueItemId(position: Int): Long {
        return itemList[position].first
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_groups, parent, false)
        return ViewHolder(view, grabHandleId, dragOnLongPress)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.bind(itemList[position])
    }

    class ViewHolder(itemView: View, grabHandleId: Int, dragOnLongPress: Boolean) : DragItemAdapter.ViewHolder(itemView,
            grabHandleId, dragOnLongPress) {

        init {
            setIsRecyclable(false)
        }

        fun bind(pair: Pair<Long, GroupRecyclerView>) = with(itemView) {
            val groupRecyclerView = pair.second

            groupRecyclerView.teamIcon?.let {
                itemView.teamIcon.setImageResource(it)
            }
            itemView.teamName.text = groupRecyclerView.teamName

            itemView.tag = groupRecyclerView.teamName
        }
    }
}