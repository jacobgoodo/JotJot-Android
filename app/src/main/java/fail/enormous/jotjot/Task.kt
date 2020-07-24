package fail.enormous.jotjot

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import fail.enormous.jotjot.db.TodoListDBContract

@Entity(tableName = TodoListDBContract.TodoListItem.TABLE_NAME)
class Task() {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    var taskId: Long? = null

    @ColumnInfo(name = TodoListDBContract.TodoListItem.COLUMN_NAME_TASK)
    var taskDetails: String? = null

    @ColumnInfo(name = TodoListDBContract.TodoListItem.COLUMN_NAME_DEADLINE)
    var taskDeadline: String? = null

    @ColumnInfo(name = TodoListDBContract.TodoListItem.COLUMN_NAME_COMPLETED)
    var completed: Boolean? = false

    @Ignore
    // deadline has been removed, keeping this constructor just in-case
    constructor(taskDetails: String?, taskDeadline: String?): this() {
        this.taskDetails = taskDetails
        this.taskDeadline = taskDeadline
    }

    constructor(taskId:Long, taskDetails: String?, taskDeadline: String?, completed: Boolean) : this(taskDetails, taskDeadline) {
        this.taskId = taskId
        this.completed = completed
    }
}