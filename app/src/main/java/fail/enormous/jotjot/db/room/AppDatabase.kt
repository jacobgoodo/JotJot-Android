package fail.enormous.jotjot.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import fail.enormous.jotjot.Task
import fail.enormous.jotjot.db.TodoListDBContract


/**
 * Created by eunice on 07/12/2017.
 */

@Database(entities = [Task::class], version = TodoListDBContract.DATABASE_VERSION)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDAO
}