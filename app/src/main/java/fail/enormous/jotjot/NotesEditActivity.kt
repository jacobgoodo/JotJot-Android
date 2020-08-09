package fail.enormous.jotjot

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import fail.enormous.jotjot.db.NotesDBContract
import fail.enormous.jotjot.db.room.AppDatabaseNotes

class NotesEditActivity : AppCompatActivity() {
    private var notesDatabase: AppDatabaseNotes? = null
    private var notesItems = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_edit)

        buildDatabase() // call BuildDatabase

        // -1 is a default value, set as impossible one to avoid unwanted data loss
        val selectedItem = intent.getIntExtra("selectedItem", -1)
        Log.d("Selected", selectedItem.toString())

        val noteTitle = notesItems[selectedItem].noteTitle
        val noteInfo = notesItems[selectedItem].noteInfo
        Log.d("noteTitle", noteTitle)
        Log.d("noteInfo", noteInfo)
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

    fun editNote(view: View) {
        // TODO: stuff when button is pressed
    }
}
