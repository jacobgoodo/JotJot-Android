package fail.enormous.jotjot.db.room

import androidx.room.*
import fail.enormous.jotjot.Task
import fail.enormous.jotjot.db.TodoListDBContract



@Dao
interface ListsDAO {

    @Query("SELECT * FROM " + TodoListDBContract.TodoListItem.TABLE_NAME)
    fun retrieveTaskList(): List<Task>

    @Insert
    fun addNewTask(task: Task): Long

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

}