package comcesar1287.github.bolocopadomundo2018.views.fragments

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.*
import com.google.firebase.firestore.DocumentReference

import comcesar1287.github.bolocopadomundo2018.R
import comcesar1287.github.bolocopadomundo2018.adapters.GroupAdapter
import comcesar1287.github.bolocopadomundo2018.firestore.firestore.services.BetService
import comcesar1287.github.bolocopadomundo2018.firestore.firestore.services.CallbackService
import comcesar1287.github.bolocopadomundo2018.firestore.firestore.services.GroupService
import comcesar1287.github.bolocopadomundo2018.models.Bet
import comcesar1287.github.bolocopadomundo2018.models.Group
import comcesar1287.github.bolocopadomundo2018.models.GroupRecyclerView
import comcesar1287.github.bolocopadomundo2018.preferences.MainPreference
import comcesar1287.github.bolocopadomundo2018.utils.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var teamIcons: TypedArray
    private lateinit var teamNames: Array<String>
    private val betService: BetService = BetService()
    private val groupService: GroupService = GroupService()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).title = ""

        setHasOptionsMenu(true)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val betReference = betService.db.document(MainPreference.getBetReference(activity))

        betService.findByReference(betReference, object : CallbackService<Bet> {
            override fun onComplete(item: Bet?) {
                item?.let {
                    teamIcons = resources.obtainTypedArray(R.array.team_icon_array)
                    teamNames = resources.getStringArray(R.array.team_name_array)

                    val groupAReference = groupService.db.document(it.groups!![0].path)
                    val groupBReference = groupService.db.document(it.groups!![1].path)
                    val groupCReference = groupService.db.document(it.groups!![2].path)
                    val groupDReference = groupService.db.document(it.groups!![3].path)
                    val groupEReference = groupService.db.document(it.groups!![4].path)
                    val groupFReference = groupService.db.document(it.groups!![5].path)
                    val groupGReference = groupService.db.document(it.groups!![6].path)
                    val groupHReference = groupService.db.document(it.groups!![7].path)

                    setupGroupA(groupAReference)
                    setupGroupB(groupBReference)
                    setupGroupC(groupCReference)
                    setupGroupD(groupDReference)
                    setupGroupE(groupEReference)
                    setupGroupF(groupFReference)
                    setupGroupG(groupGReference)
                    setupGroupH(groupHReference)
                }
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.action_share -> {
                if (checkStoragePermission()) {
                    print()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkStoragePermission(): Boolean{
        return if(ContextCompat.checkSelfPermission(activity!!,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_STORAGE)
            false
        }else{
            true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            MY_PERMISSIONS_REQUEST_STORAGE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    print()
                } else  // permission denied, boo! Disable the // functionality that depends on this permission.
                    return
            }
        }
    }

    private fun print() {
        val progressDialog = ProgressDialog.show(activity, "", getString(R.string.generate_screenshot),
                true, false)

        val bitmap = getBitmapFromView(scrollView, scrollView.getChildAt(0).height, scrollView.getChildAt(0).width)

        val file = getOutputMediaFile(".png")

        val fileOutputStream = FileOutputStream(file)

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        fileOutputStream.flush()
        fileOutputStream.close()

        progressDialog.dismiss()

        share(file)
    }

    private fun share(file: File?) {
        file?.let {
            if (it.exists()) {
                val imageUri = Uri.parse(file.absolutePath)
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                //Target whatsapp:
                shareIntent.`package` = "com.whatsapp"
                //Add text and then Image URI
                shareIntent.putExtra(Intent.EXTRA_TEXT, R.string.app_name)
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
                shareIntent.type = "image/png"
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                try {
                    startActivity(shareIntent)
                } catch (ex: android.content.ActivityNotFoundException) {
                    ex.printStackTrace()
                }

            }
        }
    }

    private fun getBitmapFromView(view: View, height: Int, width: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val backgroundDrawable = view.background

        backgroundDrawable?.let {
            backgroundDrawable.draw(canvas)
        } ?: run {
            canvas.drawColor(Color.WHITE)
        }

        view.draw(canvas)
        return bitmap
    }

    @Throws(Exception::class)
    private fun getOutputMediaFile(format: String): File? {
        // External sdcard location
        val mediaStorageDir = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                DIRECTORY_NAME)
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null
            }
        }

        // Create a media file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(Date())

        return File(mediaStorageDir.path + File.separator
                + "BET_" + timeStamp + format)
    }

    private fun setupGroupA(groupAReference: DocumentReference) {
        groupService.findByReference(groupAReference, object : CallbackService<Group> {
            override fun onComplete(item: Group?) {
                item?.first?.let {
                    val listOfTeamsGroupA = mutableListOf<Pair<Long, GroupRecyclerView>>()

                    val teamOne = GroupRecyclerView(teamIcons.getResourceId(it, -1), teamNames[it])
                    listOfTeamsGroupA.add(Pair(it.toLong(), teamOne))
                    val teamTwo = GroupRecyclerView(teamIcons.getResourceId(item.second!!, -1), teamNames[item.second!!])
                    listOfTeamsGroupA.add(Pair(item.second!!.toLong(), teamTwo))
                    val teamThree = GroupRecyclerView(teamIcons.getResourceId(item.third!!, -1), teamNames[item.third!!])
                    listOfTeamsGroupA.add(Pair(item.third!!.toLong(), teamThree))
                    val teamFour = GroupRecyclerView(teamIcons.getResourceId(item.fourth!!, -1), teamNames[item.fourth!!])
                    listOfTeamsGroupA.add(Pair(item.fourth!!.toLong(), teamFour))

                    activity?.let {
                        dragListViewGroupA.isDragEnabled = true
                        dragListViewGroupA.recyclerView.isVerticalScrollBarEnabled = true
                        dragListViewGroupA.recyclerView.isNestedScrollingEnabled = true
                        dragListViewGroupA.setLayoutManager(LinearLayoutManager(it))
                        val groupAdapter = GroupAdapter(context!!, listOfTeamsGroupA, R.id.dragHorizontal, false, item)
                        dragListViewGroupA.setAdapter(groupAdapter, true)
                        dragListViewGroupA.setCanDragHorizontally(false)
                        dragListViewGroupA.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
                    }
                } ?: run {
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
                        val groupAdapter = GroupAdapter(context!!, listOfTeamsGroupA, R.id.dragHorizontal, false, item!!)
                        dragListViewGroupA.setAdapter(groupAdapter, true)
                        dragListViewGroupA.setCanDragHorizontally(false)
                        dragListViewGroupA.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
                    }
                }
            }
        })
    }

    private fun setupGroupB(groupBReference: DocumentReference) {
        groupService.findByReference(groupBReference, object : CallbackService<Group> {
            override fun onComplete(item: Group?) {
                item?.first?.let {
                    val listOfTeamsGroupB = mutableListOf<Pair<Long, GroupRecyclerView>>()

                    val teamOne = GroupRecyclerView(teamIcons.getResourceId(it, -1), teamNames[it])
                    listOfTeamsGroupB.add(Pair(it.toLong(), teamOne))
                    val teamTwo = GroupRecyclerView(teamIcons.getResourceId(item.second!!, -1), teamNames[item.second!!])
                    listOfTeamsGroupB.add(Pair(item.second!!.toLong(), teamTwo))
                    val teamThree = GroupRecyclerView(teamIcons.getResourceId(item.third!!, -1), teamNames[item.third!!])
                    listOfTeamsGroupB.add(Pair(item.third!!.toLong(), teamThree))
                    val teamFour = GroupRecyclerView(teamIcons.getResourceId(item.fourth!!, -1), teamNames[item.fourth!!])
                    listOfTeamsGroupB.add(Pair(item.fourth!!.toLong(), teamFour))

                    activity?.let {
                        dragListViewGroupB.isDragEnabled = true
                        dragListViewGroupB.recyclerView.isVerticalScrollBarEnabled = true
                        dragListViewGroupB.recyclerView.isNestedScrollingEnabled = true
                        dragListViewGroupB.setLayoutManager(LinearLayoutManager(it))
                        val groupAdapter = GroupAdapter(context!!, listOfTeamsGroupB, R.id.dragHorizontal, false, item)
                        dragListViewGroupB.setAdapter(groupAdapter, true)
                        dragListViewGroupB.setCanDragHorizontally(false)
                        dragListViewGroupB.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
                    }
                } ?: run {
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
                        val groupAdapter = GroupAdapter(context!!, listOfTeamsGroupB, R.id.dragHorizontal, false, item!!)
                        dragListViewGroupB.setAdapter(groupAdapter, true)
                        dragListViewGroupB.setCanDragHorizontally(false)
                        dragListViewGroupB.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
                    }
                }
            }
        })
    }

    private fun setupGroupC(groupCReference: DocumentReference) {
        groupService.findByReference(groupCReference, object : CallbackService<Group> {
            override fun onComplete(item: Group?) {
                item?.first?.let {
                    val listOfTeamsGroupC = mutableListOf<Pair<Long, GroupRecyclerView>>()

                    val teamOne = GroupRecyclerView(teamIcons.getResourceId(it, -1), teamNames[it])
                    listOfTeamsGroupC.add(Pair(it.toLong(), teamOne))
                    val teamTwo = GroupRecyclerView(teamIcons.getResourceId(item.second!!, -1), teamNames[item.second!!])
                    listOfTeamsGroupC.add(Pair(item.second!!.toLong(), teamTwo))
                    val teamThree = GroupRecyclerView(teamIcons.getResourceId(item.third!!, -1), teamNames[item.third!!])
                    listOfTeamsGroupC.add(Pair(item.third!!.toLong(), teamThree))
                    val teamFour = GroupRecyclerView(teamIcons.getResourceId(item.fourth!!, -1), teamNames[item.fourth!!])
                    listOfTeamsGroupC.add(Pair(item.fourth!!.toLong(), teamFour))

                    activity?.let {
                        dragListViewGroupC.isDragEnabled = true
                        dragListViewGroupC.recyclerView.isVerticalScrollBarEnabled = true
                        dragListViewGroupC.recyclerView.isNestedScrollingEnabled = true
                        dragListViewGroupC.setLayoutManager(LinearLayoutManager(it))
                        val groupAdapter = GroupAdapter(context!!, listOfTeamsGroupC, R.id.dragHorizontal, false, item)
                        dragListViewGroupC.setAdapter(groupAdapter, true)
                        dragListViewGroupC.setCanDragHorizontally(false)
                        dragListViewGroupC.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
                    }
                } ?: run {
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
                        val groupAdapter = GroupAdapter(context!!, listOfTeamsGroupC, R.id.dragHorizontal, false, item!!)
                        dragListViewGroupC.setAdapter(groupAdapter, true)
                        dragListViewGroupC.setCanDragHorizontally(false)
                        dragListViewGroupC.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
                    }
                }
            }
        })
    }

    private fun setupGroupD(groupDReference: DocumentReference) {
        groupService.findByReference(groupDReference, object : CallbackService<Group> {
            override fun onComplete(item: Group?) {
                item?.first?.let {
                    val listOfTeamsGroupD = mutableListOf<Pair<Long, GroupRecyclerView>>()

                    val teamOne = GroupRecyclerView(teamIcons.getResourceId(it, -1), teamNames[it])
                    listOfTeamsGroupD.add(Pair(it.toLong(), teamOne))
                    val teamTwo = GroupRecyclerView(teamIcons.getResourceId(item.second!!, -1), teamNames[item.second!!])
                    listOfTeamsGroupD.add(Pair(item.second!!.toLong(), teamTwo))
                    val teamThree = GroupRecyclerView(teamIcons.getResourceId(item.third!!, -1), teamNames[item.third!!])
                    listOfTeamsGroupD.add(Pair(item.third!!.toLong(), teamThree))
                    val teamFour = GroupRecyclerView(teamIcons.getResourceId(item.fourth!!, -1), teamNames[item.fourth!!])
                    listOfTeamsGroupD.add(Pair(item.fourth!!.toLong(), teamFour))

                    activity?.let {
                        dragListViewGroupD.isDragEnabled = true
                        dragListViewGroupD.recyclerView.isVerticalScrollBarEnabled = true
                        dragListViewGroupD.recyclerView.isNestedScrollingEnabled = true
                        dragListViewGroupD.setLayoutManager(LinearLayoutManager(it))
                        val groupAdapter = GroupAdapter(context!!, listOfTeamsGroupD, R.id.dragHorizontal, false, item!!)
                        dragListViewGroupD.setAdapter(groupAdapter, true)
                        dragListViewGroupD.setCanDragHorizontally(false)
                        dragListViewGroupD.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
                    }
                } ?: run {
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
                        val groupAdapter = GroupAdapter(context!!, listOfTeamsGroupD, R.id.dragHorizontal, false, item!!)
                        dragListViewGroupD.setAdapter(groupAdapter, true)
                        dragListViewGroupD.setCanDragHorizontally(false)
                        dragListViewGroupD.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
                    }
                }
            }
        })
    }

    private fun setupGroupE(groupEReference: DocumentReference) {
        groupService.findByReference(groupEReference, object : CallbackService<Group> {
            override fun onComplete(item: Group?) {
                item?.first?.let {
                    val listOfTeamsGroupE = mutableListOf<Pair<Long, GroupRecyclerView>>()

                    val teamOne = GroupRecyclerView(teamIcons.getResourceId(it, -1), teamNames[it])
                    listOfTeamsGroupE.add(Pair(it.toLong(), teamOne))
                    val teamTwo = GroupRecyclerView(teamIcons.getResourceId(item.second!!, -1), teamNames[item.second!!])
                    listOfTeamsGroupE.add(Pair(item.second!!.toLong(), teamTwo))
                    val teamThree = GroupRecyclerView(teamIcons.getResourceId(item.third!!, -1), teamNames[item.third!!])
                    listOfTeamsGroupE.add(Pair(item.third!!.toLong(), teamThree))
                    val teamFour = GroupRecyclerView(teamIcons.getResourceId(item.fourth!!, -1), teamNames[item.fourth!!])
                    listOfTeamsGroupE.add(Pair(item.fourth!!.toLong(), teamFour))

                    activity?.let {
                        dragListViewGroupE.isDragEnabled = true
                        dragListViewGroupE.recyclerView.isVerticalScrollBarEnabled = true
                        dragListViewGroupE.recyclerView.isNestedScrollingEnabled = true
                        dragListViewGroupE.setLayoutManager(LinearLayoutManager(it))
                        val groupAdapter = GroupAdapter(context!!, listOfTeamsGroupE, R.id.dragHorizontal, false, item)
                        dragListViewGroupE.setAdapter(groupAdapter, true)
                        dragListViewGroupE.setCanDragHorizontally(false)
                        dragListViewGroupE.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
                    }
                } ?: run {
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
                        val groupAdapter = GroupAdapter(context!!, listOfTeamsGroupE, R.id.dragHorizontal, false, item!!)
                        dragListViewGroupE.setAdapter(groupAdapter, true)
                        dragListViewGroupE.setCanDragHorizontally(false)
                        dragListViewGroupE.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
                    }
                }
            }
        })
    }

    private fun setupGroupF(groupFReference: DocumentReference) {
        groupService.findByReference(groupFReference, object : CallbackService<Group> {
            override fun onComplete(item: Group?) {
                item?.first?.let {
                    val listOfTeamsGroupF = mutableListOf<Pair<Long, GroupRecyclerView>>()

                    val teamOne = GroupRecyclerView(teamIcons.getResourceId(it, -1), teamNames[it])
                    listOfTeamsGroupF.add(Pair(it.toLong(), teamOne))
                    val teamTwo = GroupRecyclerView(teamIcons.getResourceId(item.second!!, -1), teamNames[item.second!!])
                    listOfTeamsGroupF.add(Pair(item.second!!.toLong(), teamTwo))
                    val teamThree = GroupRecyclerView(teamIcons.getResourceId(item.third!!, -1), teamNames[item.third!!])
                    listOfTeamsGroupF.add(Pair(item.third!!.toLong(), teamThree))
                    val teamFour = GroupRecyclerView(teamIcons.getResourceId(item.fourth!!, -1), teamNames[item.fourth!!])
                    listOfTeamsGroupF.add(Pair(item.fourth!!.toLong(), teamFour))

                    activity?.let {
                        dragListViewGroupF.isDragEnabled = true
                        dragListViewGroupF.recyclerView.isVerticalScrollBarEnabled = true
                        dragListViewGroupF.recyclerView.isNestedScrollingEnabled = true
                        dragListViewGroupF.setLayoutManager(LinearLayoutManager(it))
                        val groupAdapter = GroupAdapter(context!!, listOfTeamsGroupF, R.id.dragHorizontal, false, item)
                        dragListViewGroupF.setAdapter(groupAdapter, true)
                        dragListViewGroupF.setCanDragHorizontally(false)
                        dragListViewGroupF.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
                    }
                } ?: run {
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
                        val groupAdapter = GroupAdapter(context!!, listOfTeamsGroupF, R.id.dragHorizontal, false, item!!)
                        dragListViewGroupF.setAdapter(groupAdapter, true)
                        dragListViewGroupF.setCanDragHorizontally(false)
                        dragListViewGroupF.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
                    }
                }
            }
        })
    }

    private fun setupGroupG(groupGReference: DocumentReference) {
        groupService.findByReference(groupGReference, object : CallbackService<Group> {
            override fun onComplete(item: Group?) {
                item?.first?.let {
                    val listOfTeamsGroupG = mutableListOf<Pair<Long, GroupRecyclerView>>()

                    val teamOne = GroupRecyclerView(teamIcons.getResourceId(it, -1), teamNames[it])
                    listOfTeamsGroupG.add(Pair(it.toLong(), teamOne))
                    val teamTwo = GroupRecyclerView(teamIcons.getResourceId(item.second!!, -1), teamNames[item.second!!])
                    listOfTeamsGroupG.add(Pair(item.second!!.toLong(), teamTwo))
                    val teamThree = GroupRecyclerView(teamIcons.getResourceId(item.third!!, -1), teamNames[item.third!!])
                    listOfTeamsGroupG.add(Pair(item.third!!.toLong(), teamThree))
                    val teamFour = GroupRecyclerView(teamIcons.getResourceId(item.fourth!!, -1), teamNames[item.fourth!!])
                    listOfTeamsGroupG.add(Pair(item.fourth!!.toLong(), teamFour))

                    activity?.let {
                        dragListViewGroupG.isDragEnabled = true
                        dragListViewGroupG.recyclerView.isVerticalScrollBarEnabled = true
                        dragListViewGroupG.recyclerView.isNestedScrollingEnabled = true
                        dragListViewGroupG.setLayoutManager(LinearLayoutManager(it))
                        val groupAdapter = GroupAdapter(context!!, listOfTeamsGroupG, R.id.dragHorizontal, false, item)
                        dragListViewGroupG.setAdapter(groupAdapter, true)
                        dragListViewGroupG.setCanDragHorizontally(false)
                        dragListViewGroupG.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
                    }
                } ?: run {
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
                        val groupAdapter = GroupAdapter(context!!, listOfTeamsGroupG, R.id.dragHorizontal, false, item!!)
                        dragListViewGroupG.setAdapter(groupAdapter, true)
                        dragListViewGroupG.setCanDragHorizontally(false)
                        dragListViewGroupG.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
                    }
                }
            }
        })
    }

    private fun setupGroupH(groupHReference: DocumentReference) {
        groupService.findByReference(groupHReference, object : CallbackService<Group> {
            override fun onComplete(item: Group?) {
                item?.first?.let {
                    val listOfTeamsGroupH = mutableListOf<Pair<Long, GroupRecyclerView>>()

                    val teamOne = GroupRecyclerView(teamIcons.getResourceId(it, -1), teamNames[it])
                    listOfTeamsGroupH.add(Pair(it.toLong(), teamOne))
                    val teamTwo = GroupRecyclerView(teamIcons.getResourceId(item.second!!, -1), teamNames[item.second!!])
                    listOfTeamsGroupH.add(Pair(item.second!!.toLong(), teamTwo))
                    val teamThree = GroupRecyclerView(teamIcons.getResourceId(item.third!!, -1), teamNames[item.third!!])
                    listOfTeamsGroupH.add(Pair(item.third!!.toLong(), teamThree))
                    val teamFour = GroupRecyclerView(teamIcons.getResourceId(item.fourth!!, -1), teamNames[item.fourth!!])
                    listOfTeamsGroupH.add(Pair(item.fourth!!.toLong(), teamFour))

                    activity?.let {
                        dragListViewGroupH.isDragEnabled = true
                        dragListViewGroupH.recyclerView.isVerticalScrollBarEnabled = true
                        dragListViewGroupH.recyclerView.isNestedScrollingEnabled = true
                        dragListViewGroupH.setLayoutManager(LinearLayoutManager(it))
                        val groupAdapter = GroupAdapter(context!!, listOfTeamsGroupH, R.id.dragHorizontal, false, item)
                        dragListViewGroupH.setAdapter(groupAdapter, true)
                        dragListViewGroupH.setCanDragHorizontally(false)
                        dragListViewGroupH.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
                    }
                } ?: run {
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
                        val groupAdapter = GroupAdapter(context!!, listOfTeamsGroupH, R.id.dragHorizontal, false, item!!)
                        dragListViewGroupH.setAdapter(groupAdapter, true)
                        dragListViewGroupH.setCanDragHorizontally(false)
                        dragListViewGroupH.setCustomDragItem(MyDragItem(it, R.layout.item_groups))
                    }
                }
            }
        })
    }
}
