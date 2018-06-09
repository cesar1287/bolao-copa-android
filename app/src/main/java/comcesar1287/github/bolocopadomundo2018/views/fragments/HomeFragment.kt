package comcesar1287.github.bolocopadomundo2018.views.fragments

import android.content.res.TypedArray
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import comcesar1287.github.bolocopadomundo2018.R
import comcesar1287.github.bolocopadomundo2018.adapters.GroupAdapter
import comcesar1287.github.bolocopadomundo2018.models.GroupRecyclerView
import comcesar1287.github.bolocopadomundo2018.utils.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var teamIcons: TypedArray
    private lateinit var teamNames: Array<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        teamIcons = resources.obtainTypedArray(R.array.team_icon_array)
        teamNames = resources.getStringArray(R.array.team_name_array)

        setupGroupA()
        setupGroupB()
        setupGroupC()
        setupGroupD()
        setupGroupE()
        setupGroupF()
        setupGroupG()
        setupGroupH()
    }

    private fun setupGroupA() {
        val listOfTeamsGroupA = mutableListOf<Pair<Long, GroupRecyclerView>>()

        val teamOne = GroupRecyclerView(teamIcons.getResourceId(RUSSIA, -1), teamNames[RUSSIA])
        listOfTeamsGroupA.add(Pair(RUSSIA.toLong(), teamOne))
        val teamTwo = GroupRecyclerView(teamIcons.getResourceId(SAUDI_ARABIA, -1), teamNames[SAUDI_ARABIA])
        listOfTeamsGroupA.add(Pair(SAUDI_ARABIA.toLong(), teamTwo))
        val teamThree = GroupRecyclerView(teamIcons.getResourceId(EGYPT, -1), teamNames[EGYPT])
        listOfTeamsGroupA.add(Pair(EGYPT.toLong(), teamThree))
        val teamFour = GroupRecyclerView(teamIcons.getResourceId(URUGUAY, -1), teamNames[URUGUAY])
        listOfTeamsGroupA.add(Pair(URUGUAY.toLong(), teamFour))

        activity?.let {
            dragListViewGroupA.isDragEnabled = true
            dragListViewGroupA.recyclerView.isVerticalScrollBarEnabled = true
            dragListViewGroupA.recyclerView.isNestedScrollingEnabled = true
            dragListViewGroupA.setLayoutManager(LinearLayoutManager(it))
            val groupAdapter = GroupAdapter(listOfTeamsGroupA, R.id.dragHorizontal, false)
            dragListViewGroupA.setAdapter(groupAdapter, true)
            dragListViewGroupA.setCanDragHorizontally(false)
            dragListViewGroupA.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
        }
    }

    private fun setupGroupB() {
        val listOfTeamsGroupB = mutableListOf<Pair<Long, GroupRecyclerView>>()

        val teamOne = GroupRecyclerView(teamIcons.getResourceId(PORTUGAL, -1), teamNames[PORTUGAL])
        listOfTeamsGroupB.add(Pair(PORTUGAL.toLong(), teamOne))
        val teamTwo = GroupRecyclerView(teamIcons.getResourceId(SPAIN, -1),teamNames[SPAIN])
        listOfTeamsGroupB.add(Pair(SPAIN.toLong(), teamTwo))
        val teamThree = GroupRecyclerView(teamIcons.getResourceId(MOROCCO, -1), teamNames[MOROCCO])
        listOfTeamsGroupB.add(Pair(MOROCCO.toLong(), teamThree))
        val teamFour = GroupRecyclerView(teamIcons.getResourceId(IRAN, -1),teamNames[IRAN])
        listOfTeamsGroupB.add(Pair(IRAN.toLong(), teamFour))

        activity?.let {
            dragListViewGroupB.isDragEnabled = true
            dragListViewGroupB.recyclerView.isVerticalScrollBarEnabled = true
            dragListViewGroupB.recyclerView.isNestedScrollingEnabled = true
            dragListViewGroupB.setLayoutManager(LinearLayoutManager(it))
            val groupAdapter = GroupAdapter(listOfTeamsGroupB, R.id.dragHorizontal, false)
            dragListViewGroupB.setAdapter(groupAdapter, true)
            dragListViewGroupB.setCanDragHorizontally(false)
            dragListViewGroupB.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
        }
    }

    private fun setupGroupC() {
        val listOfTeamsGroupC = mutableListOf<Pair<Long, GroupRecyclerView>>()

        val teamOne = GroupRecyclerView(teamIcons.getResourceId(FRANCE, -1), teamNames[FRANCE])
        listOfTeamsGroupC.add(Pair(FRANCE.toLong(), teamOne))
        val teamTwo = GroupRecyclerView(teamIcons.getResourceId(AUSTRALIA, -1), teamNames[AUSTRALIA])
        listOfTeamsGroupC.add(Pair(AUSTRALIA.toLong(), teamTwo))
        val teamThree = GroupRecyclerView(teamIcons.getResourceId(PERU, -1), teamNames[PERU])
        listOfTeamsGroupC.add(Pair(PERU.toLong(), teamThree))
        val teamFour = GroupRecyclerView(teamIcons.getResourceId(DENMARK, -1), teamNames[DENMARK])
        listOfTeamsGroupC.add(Pair(DENMARK.toLong(), teamFour))

        activity?.let {
            dragListViewGroupC.isDragEnabled = true
            dragListViewGroupC.recyclerView.isVerticalScrollBarEnabled = true
            dragListViewGroupC.recyclerView.isNestedScrollingEnabled = true
            dragListViewGroupC.setLayoutManager(LinearLayoutManager(it))
            val groupAdapter = GroupAdapter(listOfTeamsGroupC, R.id.dragHorizontal, false)
            dragListViewGroupC.setAdapter(groupAdapter, true)
            dragListViewGroupC.setCanDragHorizontally(false)
            dragListViewGroupC.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
        }
    }

    private fun setupGroupD() {
        val listOfTeamsGroupD = mutableListOf<Pair<Long, GroupRecyclerView>>()

        val teamOne = GroupRecyclerView(teamIcons.getResourceId(ARGENTINA, -1), teamNames[ARGENTINA])
        listOfTeamsGroupD.add(Pair(ARGENTINA.toLong(), teamOne))
        val teamTwo = GroupRecyclerView(teamIcons.getResourceId(ICELAND, -1), teamNames[ICELAND])
        listOfTeamsGroupD.add(Pair(ICELAND.toLong(), teamTwo))
        val teamThree = GroupRecyclerView(teamIcons.getResourceId(CROATIA, -1), teamNames[CROATIA])
        listOfTeamsGroupD.add(Pair(CROATIA.toLong(), teamThree))
        val teamFour = GroupRecyclerView(teamIcons.getResourceId(NIGERIA, -1), teamNames[NIGERIA])
        listOfTeamsGroupD.add(Pair(NIGERIA.toLong(), teamFour))

        activity?.let {
            dragListViewGroupD.isDragEnabled = true
            dragListViewGroupD.recyclerView.isVerticalScrollBarEnabled = true
            dragListViewGroupD.recyclerView.isNestedScrollingEnabled = true
            dragListViewGroupD.setLayoutManager(LinearLayoutManager(it))
            val groupAdapter = GroupAdapter(listOfTeamsGroupD, R.id.dragHorizontal, false)
            dragListViewGroupD.setAdapter(groupAdapter, true)
            dragListViewGroupD.setCanDragHorizontally(false)
            dragListViewGroupD.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
        }
    }

    private fun setupGroupE() {
        val listOfTeamsGroupE = mutableListOf<Pair<Long, GroupRecyclerView>>()

        val teamOne = GroupRecyclerView(teamIcons.getResourceId(BRAZIL, -1), teamNames[BRAZIL])
        listOfTeamsGroupE.add(Pair(BRAZIL.toLong(), teamOne))
        val teamTwo = GroupRecyclerView(teamIcons.getResourceId(SWITZERLAND, -1), teamNames[SWITZERLAND])
        listOfTeamsGroupE.add(Pair(SWITZERLAND.toLong(), teamTwo))
        val teamThree = GroupRecyclerView(teamIcons.getResourceId(COSTA_RICA, -1), teamNames[COSTA_RICA])
        listOfTeamsGroupE.add(Pair(COSTA_RICA.toLong(), teamThree))
        val teamFour = GroupRecyclerView(teamIcons.getResourceId(SERBIA, -1), teamNames[SERBIA])
        listOfTeamsGroupE.add(Pair(SERBIA.toLong(), teamFour))

        activity?.let {
            dragListViewGroupE.isDragEnabled = true
            dragListViewGroupE.recyclerView.isVerticalScrollBarEnabled = true
            dragListViewGroupE.recyclerView.isNestedScrollingEnabled = true
            dragListViewGroupE.setLayoutManager(LinearLayoutManager(it))
            val groupAdapter = GroupAdapter(listOfTeamsGroupE, R.id.dragHorizontal, false)
            dragListViewGroupE.setAdapter(groupAdapter, true)
            dragListViewGroupE.setCanDragHorizontally(false)
            dragListViewGroupE.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
        }
    }

    private fun setupGroupF() {
        val listOfTeamsGroupF = mutableListOf<Pair<Long, GroupRecyclerView>>()

        val teamOne = GroupRecyclerView(teamIcons.getResourceId(GERMANY, -1), teamNames[GERMANY])
        listOfTeamsGroupF.add(Pair(GERMANY.toLong(), teamOne))
        val teamTwo = GroupRecyclerView(teamIcons.getResourceId(MEXICO, -1), teamNames[MEXICO])
        listOfTeamsGroupF.add(Pair(MEXICO.toLong(), teamTwo))
        val teamThree = GroupRecyclerView(teamIcons.getResourceId(SWEDEN, -1), teamNames[SWEDEN])
        listOfTeamsGroupF.add(Pair(SWEDEN.toLong(), teamThree))
        val teamFour = GroupRecyclerView(teamIcons.getResourceId(SOUTH_KOREA, -1), teamNames[SOUTH_KOREA])
        listOfTeamsGroupF.add(Pair(SOUTH_KOREA.toLong(), teamFour))

        activity?.let {
            dragListViewGroupF.isDragEnabled = true
            dragListViewGroupF.recyclerView.isVerticalScrollBarEnabled = true
            dragListViewGroupF.recyclerView.isNestedScrollingEnabled = true
            dragListViewGroupF.setLayoutManager(LinearLayoutManager(it))
            val groupAdapter = GroupAdapter(listOfTeamsGroupF, R.id.dragHorizontal, false)
            dragListViewGroupF.setAdapter(groupAdapter, true)
            dragListViewGroupF.setCanDragHorizontally(false)
            dragListViewGroupF.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
        }
    }

    private fun setupGroupG() {
        val listOfTeamsGroupG = mutableListOf<Pair<Long, GroupRecyclerView>>()

        val teamOne = GroupRecyclerView(teamIcons.getResourceId(BELGIUM, -1), teamNames[BELGIUM])
        listOfTeamsGroupG.add(Pair(BELGIUM.toLong(), teamOne))
        val teamTwo = GroupRecyclerView(teamIcons.getResourceId(PANAMA, -1), teamNames[PANAMA])
        listOfTeamsGroupG.add(Pair(PANAMA.toLong(), teamTwo))
        val teamThree = GroupRecyclerView(teamIcons.getResourceId(TUNISIA, -1), teamNames[TUNISIA])
        listOfTeamsGroupG.add(Pair(TUNISIA.toLong(), teamThree))
        val teamFour = GroupRecyclerView(teamIcons.getResourceId(ENGLAND, -1), teamNames[ENGLAND])
        listOfTeamsGroupG.add(Pair(ENGLAND.toLong(), teamFour))

        activity?.let {
            dragListViewGroupG.isDragEnabled = true
            dragListViewGroupG.recyclerView.isVerticalScrollBarEnabled = true
            dragListViewGroupG.recyclerView.isNestedScrollingEnabled = true
            dragListViewGroupG.setLayoutManager(LinearLayoutManager(it))
            val groupAdapter = GroupAdapter(listOfTeamsGroupG, R.id.dragHorizontal, false)
            dragListViewGroupG.setAdapter(groupAdapter, true)
            dragListViewGroupG.setCanDragHorizontally(false)
            dragListViewGroupG.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
        }
    }

    private fun setupGroupH() {
        val listOfTeamsGroupH = mutableListOf<Pair<Long, GroupRecyclerView>>()

        val teamOne = GroupRecyclerView(teamIcons.getResourceId(POLAND, -1), teamNames[POLAND])
        listOfTeamsGroupH.add(Pair(POLAND.toLong(), teamOne))
        val teamTwo = GroupRecyclerView(teamIcons.getResourceId(SENEGAL, -1), teamNames[SENEGAL])
        listOfTeamsGroupH.add(Pair(SENEGAL.toLong(), teamTwo))
        val teamThree = GroupRecyclerView(teamIcons.getResourceId(COLOMBIA, -1), teamNames[COLOMBIA])
        listOfTeamsGroupH.add(Pair(COLOMBIA.toLong(), teamThree))
        val teamFour = GroupRecyclerView(teamIcons.getResourceId(JAPAN, -1), teamNames[JAPAN])
        listOfTeamsGroupH.add(Pair(JAPAN.toLong(), teamFour))

        activity?.let {
            dragListViewGroupH.isDragEnabled = true
            dragListViewGroupH.recyclerView.isVerticalScrollBarEnabled = true
            dragListViewGroupH.recyclerView.isNestedScrollingEnabled = true
            dragListViewGroupH.setLayoutManager(LinearLayoutManager(it))
            val groupAdapter = GroupAdapter(listOfTeamsGroupH, R.id.dragHorizontal, false)
            dragListViewGroupH.setAdapter(groupAdapter, true)
            dragListViewGroupH.setCanDragHorizontally(false)
            dragListViewGroupH.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
        }
    }
}
