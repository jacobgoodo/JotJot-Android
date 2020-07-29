package fail.enormous.jotjot.db

import android.provider.BaseColumns

// NB: Why is it called To Do List when it's Lists in the app? Well,
// To Do List did not fit the simplistic design, so the name was shortened
// to match with the rest of JotJot :)

object TodoListDBContract {

        const val DATABASE_VERSION = 2
        const val DATABASE_NAME = "todo_list_db"

    class TodoListItem: BaseColumns {
        companion object {
            const val TABLE_NAME = "todo_list_item"
            const val COLUMN_NAME_TASK = "task_details"
            const val COLUMN_NAME_DEADLINE = "task_deadline" // deadline no longer used
            const val COLUMN_NAME_COMPLETED = "task_completed"
        }
    }

}