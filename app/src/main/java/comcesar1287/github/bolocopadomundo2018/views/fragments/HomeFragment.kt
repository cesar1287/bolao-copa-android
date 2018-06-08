package comcesar1287.github.bolocopadomundo2018.views.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.woxthebox.draglistview.DragListView

import comcesar1287.github.bolocopadomundo2018.R
import comcesar1287.github.bolocopadomundo2018.adapters.GroupAdapter
import comcesar1287.github.bolocopadomundo2018.models.GroupRecyclerView
import comcesar1287.github.bolocopadomundo2018.utils.MyDragItem
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupGroupA()
        setupGroupB()
        setupGroupC()
    }

    private fun setupGroupA() {
        val listOfTeamsGroupA = mutableListOf<Pair<Long, GroupRecyclerView>>()

        val teamOne = GroupRecyclerView(R.drawable.ic_russia, "Rússia")
        listOfTeamsGroupA.add(Pair(1, teamOne))
        val teamTwo = GroupRecyclerView(R.drawable.ic_saudi_arabia, "Arábia Saudita")
        listOfTeamsGroupA.add(Pair(2, teamTwo))
        val teamThree = GroupRecyclerView(R.drawable.ic_egypt, "Egito")
        listOfTeamsGroupA.add(Pair(3, teamThree))
        val teamFour = GroupRecyclerView(R.drawable.ic_uruguay, "Uruguai")
        listOfTeamsGroupA.add(Pair(4, teamFour))

        activity?.let {
            dragListViewGroupA.isDragEnabled = true
            dragListViewGroupA.recyclerView.isVerticalScrollBarEnabled = true
            dragListViewGroupA.recyclerView.isNestedScrollingEnabled = true
            dragListViewGroupA.setDragListListener(object : DragListView.DragListListenerAdapter() {
                override fun onItemDragEnded(fromPosition: Int, toPosition: Int) {
                    super.onItemDragEnded(fromPosition, toPosition)

                    //Toast.makeText(dragListView.context, "End - position: $toPosition", Toast.LENGTH_SHORT).show();
                }
            })

            dragListViewGroupA.setLayoutManager(LinearLayoutManager(it))
            val groupAdapter = GroupAdapter(listOfTeamsGroupA, R.id.dragHorizontal, false)
            dragListViewGroupA.setAdapter(groupAdapter, true)
            dragListViewGroupA.setCanDragHorizontally(false)
            dragListViewGroupA.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
        }
    }

    private fun setupGroupB() {
        val listOfTeamsGroupB = mutableListOf<Pair<Long, GroupRecyclerView>>()

        val teamOne = GroupRecyclerView(R.drawable.ic_portugal, "Portugal")
        listOfTeamsGroupB.add(Pair(1, teamOne))
        val teamTwo = GroupRecyclerView(R.drawable.ic_spain, "Espanha")
        listOfTeamsGroupB.add(Pair(2, teamTwo))
        val teamThree = GroupRecyclerView(R.drawable.ic_morocco, "Marrocos")
        listOfTeamsGroupB.add(Pair(3, teamThree))
        val teamFour = GroupRecyclerView(R.drawable.ic_iran, "Irã")
        listOfTeamsGroupB.add(Pair(4, teamFour))

        activity?.let {
            dragListViewGroupB.isDragEnabled = true
            dragListViewGroupB.recyclerView.isVerticalScrollBarEnabled = true
            dragListViewGroupB.recyclerView.isNestedScrollingEnabled = true
            dragListViewGroupB.setDragListListener(object : DragListView.DragListListenerAdapter() {
                override fun onItemDragEnded(fromPosition: Int, toPosition: Int) {
                    super.onItemDragEnded(fromPosition, toPosition)

                    //Toast.makeText(dragListView.context, "End - position: $toPosition", Toast.LENGTH_SHORT).show();
                }
            })

            dragListViewGroupB.setLayoutManager(LinearLayoutManager(it))
            val groupAdapter = GroupAdapter(listOfTeamsGroupB, R.id.dragHorizontal, false)
            dragListViewGroupB.setAdapter(groupAdapter, true)
            dragListViewGroupB.setCanDragHorizontally(false)
            dragListViewGroupB.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
        }
    }

    private fun setupGroupC() {
        val listOfTeamsGroupC = mutableListOf<Pair<Long, GroupRecyclerView>>()

        val teamOne = GroupRecyclerView(R.drawable.ic_france, "França")
        listOfTeamsGroupC.add(Pair(1, teamOne))
        val teamTwo = GroupRecyclerView(R.drawable.ic_peru, "Peru")
        listOfTeamsGroupC.add(Pair(2, teamTwo))
        val teamThree = GroupRecyclerView(R.drawable.ic_australia, "Austrália")
        listOfTeamsGroupC.add(Pair(3, teamThree))
        val teamFour = GroupRecyclerView(R.drawable.ic_denmark, "Dinamarca")
        listOfTeamsGroupC.add(Pair(4, teamFour))

        activity?.let {
            dragListViewGroupC.isDragEnabled = true
            dragListViewGroupC.recyclerView.isVerticalScrollBarEnabled = true
            dragListViewGroupC.recyclerView.isNestedScrollingEnabled = true
            dragListViewGroupC.setDragListListener(object : DragListView.DragListListenerAdapter() {
                override fun onItemDragEnded(fromPosition: Int, toPosition: Int) {
                    super.onItemDragEnded(fromPosition, toPosition)

                    //Toast.makeText(dragListView.context, "End - position: $toPosition", Toast.LENGTH_SHORT).show();
                }
            })

            dragListViewGroupC.setLayoutManager(LinearLayoutManager(it))
            val groupAdapter = GroupAdapter(listOfTeamsGroupC, R.id.dragHorizontal, false)
            dragListViewGroupC.setAdapter(groupAdapter, true)
            dragListViewGroupC.setCanDragHorizontally(false)
            dragListViewGroupC.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
        }
    }
}
