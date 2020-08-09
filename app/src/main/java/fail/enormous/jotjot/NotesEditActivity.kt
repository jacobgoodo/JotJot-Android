package fail.enormous.jotjot

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import fail.enormous.jotjot.db.NotesDBContract
import fail.enormous.jotjot.db.room.AppDatabaseNotes
import kotlinx.android.synthetic.main.activity_notes_edit.*

class NotesEditActivity : AppCompatActivity() {
    private var notesDatabase: AppDatabaseNotes? = null
    private var notesItems = ArrayList<Note>()
    private var listAdapter: NotesListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_edit)

        buildDatabase() // call BuildDatabase

        // -1 is a default value, set as impossible one to avoid unwanted data loss
        val selectedItem = intent.getIntExtra("selectedItem", -1)
        Log.d("Selected", selectedItem.toString())

        // Display the text of Note tapped on
        displayText(selectedItem)

        findViewById<Button>(R.id.notesEditButton).setOnClickListener{
            editNote(selectedItem)
        }
    }

    private fun buildDatabase() {
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

    private fun displayText(selectedItem: Int) {
        // Ask the database for the title and info of the note and define them
        notesItems =
            NotesActivity.RetrieveNotesAsyncTask(notesDatabase).execute().get() as ArrayList<Note>
        val noteTitle = notesItems[selectedItem].noteTitle
        val noteInfo = notesItems[selectedItem].noteInfo
        Log.d("noteTitle", noteTitle)
        Log.d("noteInfo", noteInfo)

        // Set the text of each EditText as the variables retrieved from database
        note_title.setText(noteTitle)
        note_content.setText(noteInfo)
    }

    private fun editNote(selectedItem: Int) {
        Log.d("Editing note", selectedItem.toString())
        val titleTex = this.findViewById<EditText>(R.id.note_title)
        val contentTex = this.findViewById<EditText>(R.id.note_content)
        val noteTitle = titleTex.text.toString()
        val noteContent = contentTex.text.toString()

        notesItems[selectedItem].noteTitle = noteTitle
        notesItems[selectedItem].noteInfo = noteContent

        NotesActivity.UpdateNotesAsyncTask(notesDatabase, notesItems[selectedItem]).execute()
        listAdapter?.notifyDataSetChanged()
        startActivity(Intent(this, NotesActivity::class.java))
    }
}
