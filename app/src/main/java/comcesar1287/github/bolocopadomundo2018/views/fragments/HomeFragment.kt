package comcesar1287.github.bolocopadomundo2018.views.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.woxthebox.draglistview.DragListView

import comcesar1287.github.bolocopadomundo2018.R
import comcesar1287.github.bolocopadomundo2018.adapters.GroupAdapter
import comcesar1287.github.bolocopadomundo2018.models.GroupRecyclerView
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listOfTeams = mutableListOf<Pair<Long, GroupRecyclerView>>()

        val teamOne = GroupRecyclerView(R.drawable.ic_brazil, "Brasil")
        listOfTeams.add(Pair(1, teamOne))
        val teamTwo = GroupRecyclerView(R.drawable.ic_brazil, "Alemanha")
        listOfTeams.add(Pair(2, teamTwo))
        val teamThree = GroupRecyclerView(R.drawable.ic_brazil, "Argentina")
        listOfTeams.add(Pair(3, teamThree))
        val teamFour = GroupRecyclerView(R.drawable.ic_brazil, "França")
        listOfTeams.add(Pair(4, teamFour))

        activity?.let {
            dragListView.isDragEnabled = true
            dragListView.recyclerView.isVerticalScrollBarEnabled = true
            dragListView.setDragListListener(object : DragListView.DragListListenerAdapter() {
                override fun onItemDragEnded(fromPosition: Int, toPosition: Int) {
                    super.onItemDragEnded(fromPosition, toPosition)

                    Toast.makeText(dragListView.context, "End - position: $toPosition", Toast.LENGTH_SHORT).show();
                }

                override fun onItemDragStarted(position: Int) {
                    super.onItemDragStarted(position)

                    Toast.makeText(dragListView.context, "Start - position: $position", Toast.LENGTH_SHORT).show();
                }
            })

            dragListView.setLayoutManager(LinearLayoutManager(it))
            val groupAdapter = GroupAdapter(listOfTeams, R.id.dragHorizontal, false)
            dragListView.setAdapter(groupAdapter, true)
            dragListView.setCanDragHorizontally(false)
        }
    }
}
