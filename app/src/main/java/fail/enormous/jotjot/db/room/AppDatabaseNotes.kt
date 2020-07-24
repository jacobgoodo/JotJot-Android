package fail.enormous.jotjot.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import fail.enormous.jotjot.Note
import fail.enormous.jotjot.db.NotesDBContract


@Database(entities = [Note::class], version = NotesDBContract.DATABASE_VERSION)
abstract class AppDatabaseNotes : RoomDatabase() {
    abstract fun notesDao(): NotesDAO
}