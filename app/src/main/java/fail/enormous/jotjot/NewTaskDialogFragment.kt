@file:Suppress("DEPRECATION")

package fail.enormous.jotjot

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.widget.EditText



class NewTaskDialogFragment: DialogFragment() {

    // Interface base
    interface NewTaskDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment, taskDetails: String)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    private var newTaskDialogListener: NewTaskDialogListener? = null

    // When instance of dialogue is created
    companion object {
        fun newInstance(title: Int, selected: String?): NewTaskDialogFragment {

            val newTaskDialogFragment = NewTaskDialogFragment()
            val args = Bundle()
            args.putInt("dialog_title", title)
            args.putString("selected_item", selected)
            newTaskDialogFragment.arguments = args
            return newTaskDialogFragment
        }
    }

    // On dialogue popup
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = arguments.getInt("dialog_title")
        val selectedText = arguments.getString("selected_item")
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)

        val dialogView = activity.layoutInflater.inflate(R.layout.dialog_new_task, null)

        val task = dialogView.findViewById<EditText>(R.id.task)

        task.setText(selectedText)


        builder.setView(dialogView)
                .setPositiveButton(R.string.save) { dialog, id ->

                    newTaskDialogListener?.onDialogPositiveClick(this, task.text.toString());
                }
            .setNegativeButton(android.R.string.cancel) { dialog, id ->

                newTaskDialogListener?.onDialogNegativeClick(this)
            }

        return builder.create()
    }

    // Error handling
    @Throws(ClassCastException::class)
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            newTaskDialogListener = activity as NewTaskDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + "Error in NewTaskDialogListener.kt")
        }

    }
}