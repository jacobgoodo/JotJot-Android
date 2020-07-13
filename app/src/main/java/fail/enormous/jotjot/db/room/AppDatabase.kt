package fail.enormous.jotjot.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import fail.enormous.jotjot.Task
import fail.enormous.jotjot.db.TodoListDBContract


@Database(entities = [Task::class], version = TodoListDBContract.DATABASE_VERSION)
abstract class AppDatabase : RoomDatabase() {
    abstract fun listsDao(): ListsDAO
}