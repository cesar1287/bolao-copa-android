package comcesar1287.github.bolocopadomundo2018.utils

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.woxthebox.draglistview.DragItem
import comcesar1287.github.bolocopadomundo2018.R

class MyDragItem(private val context: Context, layoutId: Int): DragItem(context, layoutId) {

    override fun onBindDragView(clickedView: View?, dragView: View?) {
        super.onBindDragView(clickedView, dragView)

        val text = clickedView?.findViewById<TextView>(R.id.teamName)?.text
        dragView?.findViewById<TextView>(R.id.teamName)?.text = text

        dragView?.setBackgroundColor(ContextCompat.getColor(context, R.color.grey_100))
    }
}