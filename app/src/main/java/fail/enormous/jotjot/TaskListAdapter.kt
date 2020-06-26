package fail.enormous.jotjot

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class TaskListAdapter(val context: Context, private val taskList: ArrayList<Task>): BaseAdapter() {

    private var inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        var view = convertView
        val viewHolder: ViewHolder?
        if (view == null) {
            view =  inflater.inflate(R.layout.list_item_task, parent, false)
            viewHolder = ViewHolder()
            viewHolder.taskDescriptionTextView = view.findViewById(R.id.task_item_description)
            // Obsolete deadlines and completion status. Decided that this goes against the simplistic concept
            //viewHolder.deadlineTextView = view.findViewById(R.id.task_item_deadline)
           // viewHolder.statusTextView = view.findViewById(R.id.task_item_status)

            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder?
        }

        val taskDescriptionTextView = viewHolder?.taskDescriptionTextView
        //val deadlineTextView = viewHolder?.deadlineTextView
        //val statusTextView = viewHolder?.statusTextView

        val task = getItem(position) as Task

        taskDescriptionTextView?.text = task.taskDetails
        /*
        OBSOLETE - Removed deadlines :(
        deadlineTextView?.text = task.taskDeadline
        if (null != task.completed && true == task.completed) {
            statusTextView?.text = (context.getString(R.string.complete))
            statusTextView?.setTextColor(ResourcesCompat.getColor(context.resources, android.R.color.holo_green_light, null))

        } else {
            statusTextView?.text = (context.getString(R.string.incomplete))
            statusTextView?.setTextColor(ResourcesCompat.getColor(context.resources, android.R.color.holo_red_light, null))
        }
        */

        return view
    }

    override fun getItem(position: Int): Any {
        return taskList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return taskList.size
    }

    private class ViewHolder {
        var taskDescriptionTextView: TextView? = null
        var deadlineTextView: TextView? = null
        var statusTextView: TextView? = null
    }
}