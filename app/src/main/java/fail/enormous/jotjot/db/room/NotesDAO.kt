package fail.enormous.jotjot.db.room

import androidx.room.*
import fail.enormous.jotjot.Note
import fail.enormous.jotjot.db.NotesDBContract



@Dao
interface NotesDAO {

    @Query("SELECT * FROM " + NotesDBContract.NotesItem.TABLE_NAME)
    fun retrieveNoteList(): List<Note>

    @Insert
    fun addNewNote(task: Note): Long

    @Update
    fun updateNote(task: Note)

    @Delete
    fun deleteNote(task: Note)

}