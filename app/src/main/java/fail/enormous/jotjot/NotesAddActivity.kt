package fail.enormous.jotjot

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import fail.enormous.jotjot.db.NotesDBContract
import fail.enormous.jotjot.db.room.AppDatabaseNotes

class NotesAddActivity : AppCompatActivity() {
    private var notesDatabase: AppDatabaseNotes? = null
    private var listAdapter: NotesListAdapter? = null
    private var notesItems = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // Force portrait mode
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_add)

        notesDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabaseNotes::class.java,
            NotesDBContract.DATABASE_NAME
        ) // Defining the database
            .addMigrations(object : Migration(
                NotesDBContract.DATABASE_VERSION - 1,
                NotesDBContract.DATABASE_VERSION
            ) {
                override fun migrate(database: SupportSQLiteDatabase) { // use Room (which stores as SQL database because Google wants it that way)
                }
            }).build() // build database based on parameters

    }

    // createNote() defined by android:onClick
    fun createNote(view: View) : Boolean {
        val titleTex = this.findViewById<EditText>(R.id.note_title)
        val contentTex = this.findViewById<EditText>(R.id.note_content)

        // Defining values for the text entered into each EditText
        val noteTitle = titleTex.text.toString()
        val noteContent = contentTex.text.toString()
        Log.d("Title", noteTitle)
        Log.d("Content", noteContent)

        addNoteToDatabase(noteTitle, noteContent) // Add note to database
        startActivity(Intent(this, NotesActivity::class.java)) // Go back to notes list
        return true
    }

    private fun addNoteToDatabase(noteTitle: String, noteContent: String) {
        val addNewNote = Note(noteTitle, "")
        val addNewNoteDesc = Note(noteContent, "")

        addNewNote.noteId = AddNoteAsyncTask(notesDatabase, addNewNote).execute().get()
        addNewNoteDesc.noteId = AddNoteAsyncTask(notesDatabase, addNewNoteDesc).execute().get()
        notesItems.add(addNewNote)
        notesItems.add(addNewNoteDesc)
        listAdapter?.notifyDataSetChanged()

    }

    private class AddNoteAsyncTask(private val database: AppDatabaseNotes?, private val newNote: Note) :
        AsyncTask<Void, Void, Long>() {

        override fun doInBackground(vararg params: Void): Long? {
            return database?.notesDao()?.addNewNote(newNote)
        }
    }
}

