package fail.enormous.jotjot

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.AsyncTask
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fail.enormous.jotjot.db.NotesDBContract
import fail.enormous.jotjot.db.room.AppDatabaseNotes

class NotesActivity : AppCompatActivity() {
    private var notesDatabase: AppDatabaseNotes? = null
    private var listAdapter: NotesListAdapter? = null
    private var notesItems = ArrayList<Note>()
    private var listView: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // Force portrait mode
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        // Database stuffs - as declared in NotesAddActivity
        // Would be best implemented globally but Android isn't liking when I try and do that :(
        notesDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabaseNotes::class.java,
            NotesDBContract.DATABASE_NAME
        )
            .addMigrations(object : Migration(
                NotesDBContract.DATABASE_VERSION - 1,
                NotesDBContract.DATABASE_VERSION
            ) {
                override fun migrate(database: SupportSQLiteDatabase) {
                }
            }).build()

        val notesAddButton = findViewById<FloatingActionButton>(R.id.notesAddButton) // Reminders Floating Action Button
        notesAddButton.setOnClickListener{
            val showNotesAddActivity = Intent(this, NotesAddActivity::class.java) // show RemindersAddActivity declaration
            startActivity(showNotesAddActivity)

            populateListView() // fill ListView with items from database

            listView = findViewById(R.id.notes_list_view)

        }
    }

    override fun onBackPressed() {
        val showMainActivity = Intent(this, MainActivity::class.java)
        startActivity(showMainActivity) // Go to MainActivity on back button press
    }

    private fun populateListView() {
        notesItems = RetrieveNotesAsyncTask(notesDatabase).execute().get() as ArrayList<Note>
        listAdapter = NotesListAdapter(this, notesItems)
        listView?.adapter = listAdapter
    }

    private class RetrieveNotesAsyncTask(private val database: AppDatabaseNotes?) :
        AsyncTask<Void, Void, List<Note>>() {

        override fun doInBackground(vararg params: Void): List<Note>? {
            return database?.notesDao()?.retrieveNoteList()
        }
    }

}