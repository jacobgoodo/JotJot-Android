package fail.enormous.jotjot

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class NotesListAdapter(val context: Context, private val noteList: ArrayList<Note>): BaseAdapter() {

    private var inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        var view = convertView
        val viewHolder: ViewHolder?
        if (view == null) {
            view =  inflater.inflate(R.layout.list_note, parent, false)
            viewHolder = ViewHolder()
            viewHolder.noteTitleTextView = view.findViewById(R.id.note_item_title)
            viewHolder.noteContentTextView = view.findViewById(R.id.note_item_content)
            view.tag = viewHolder
        }

        else {
            viewHolder = view.tag as ViewHolder?
        }

        val noteTitleTextView = viewHolder?.noteTitleTextView
        val noteContentTextView = viewHolder?.noteContentTextView

        val note = getItem(position) as Note

        noteTitleTextView?.text = note.noteTitle
        noteContentTextView?.text = note.noteInfo
        return view
    }

    override fun getItem(position: Int): Any {
        return noteList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return noteList.size
    }

    private class ViewHolder {
        var noteTitleTextView: TextView? = null
        var noteContentTextView: TextView? = null
    }
}