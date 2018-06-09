package comcesar1287.github.bolocopadomundo2018.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.woxthebox.draglistview.DragItemAdapter
import comcesar1287.github.bolocopadomundo2018.R
import comcesar1287.github.bolocopadomundo2018.firestore.firestore.services.GroupService
import comcesar1287.github.bolocopadomundo2018.models.Group
import comcesar1287.github.bolocopadomundo2018.models.GroupRecyclerView
import kotlinx.android.synthetic.main.item_groups.view.*

class GroupAdapter(val context: Context, list: MutableList<Pair<Long, GroupRecyclerView>>, private val grabHandleId: Int,
                   private val dragOnLongPress: Boolean, private val group: Group): DragItemAdapter<Pair<Long, GroupRecyclerView>,
        GroupAdapter.ViewHolder>() {

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
        holder.bind(itemList[position], itemList, group, context)
    }

    class ViewHolder(itemView: View, grabHandleId: Int, dragOnLongPress: Boolean) : DragItemAdapter.ViewHolder(itemView,
            grabHandleId, dragOnLongPress) {

        init {
            setIsRecyclable(false)
        }

        fun bind(pair: Pair<Long, GroupRecyclerView>, itemList: MutableList<Pair<Long, GroupRecyclerView>>, group: Group,
                 context: Context) = with(itemView) {

            val teamNames = context.resources.getStringArray(R.array.team_name_array)
            val groupService = GroupService()

            val groupRecyclerView = pair.second

            groupRecyclerView.teamIcon?.let {
                itemView.teamIcon.setImageResource(it)
            }
            itemView.teamName.text = groupRecyclerView.teamName

            itemView.tag = groupRecyclerView.teamName

            itemView.teamPosition.text = "${adapterPosition+1}º"

            group.first = teamNames.indexOf(itemList[0].second.teamName)
            group.second = teamNames.indexOf(itemList[1].second.teamName)
            group.third = teamNames.indexOf(itemList[2].second.teamName)
            group.fourth = teamNames.indexOf(itemList[3].second.teamName)

            groupService.add(group)
        }
    }
}