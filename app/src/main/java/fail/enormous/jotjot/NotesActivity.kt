package fail.enormous.jotjot

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import fail.enormous.jotjot.db.NotesDBContract
import fail.enormous.jotjot.db.room.AppDatabaseNotes
import kotlinx.android.synthetic.main.activity_lists.*

class NotesActivity : AppCompatActivity() {
    private var notesDatabase: AppDatabaseNotes? = null
    private var listAdapter: NotesListAdapter? = null
    private var notesItems = ArrayList<Note>()
    private var listView: ListView? = null
    private var selectedItem = -1 // Make selectedItem a value BELOW anything possible 😎

    override fun onCreate(savedInstanceState: Bundle?) {
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // Force portrait mode
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        listView = findViewById(R.id.notes_list_view)
        val lv = findViewById<ListView>(R.id.notes_list_view)

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
        }
        populateListView() // fill ListView with items from database


        // When a ListView item is long-pressed
        lv.onItemLongClickListener =
            AdapterView.OnItemLongClickListener { _, _, currentItem, _ ->
                selectedItem = currentItem
                Log.v("ListView long-press", "pos: $selectedItem")
                deleteItems(selectedItem)
            }
        // When a ListView item is tapped
        lv.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, currentItem, _ ->
                selectedItem = currentItem
                Log.v("ListView tap", "pos: $selectedItem")
                editItems(selectedItem)
            }

    }

    private fun editItems(selectedItem: Int): Boolean  {
        // TODO: Edit function
        return true
    }

    private fun deleteItems(selectedItem: Int): Boolean {
        // selectedTask = value of selectedItem (long-pressed in ListView)
        val selectedTask = notesItems[selectedItem]
        // Execute a deletion of the long-pressed item
       // NotesActivity.DeleteTaskAsyncTask(notesDatabase, selectedTask).execute()
        // Remove from the ArrayList
        notesItems.removeAt(selectedItem)
        // Connect to the Adapter, indicating a change of data (ListView needs to be updated)
        listAdapter?.notifyDataSetChanged()
        // Reset selectedItem to impossible value
        this.selectedItem = -1
        // Display deleted item snackbar
        Snackbar.make(listsAddButton, R.string.task_delete, Snackbar.LENGTH_SHORT)
            .setAction("Action", null).show()
        // Return true because this needs a Boolean to work, Android Studio gets a bit angry and won't compile otherwise
        return true
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

    /*
    private class DeleteTaskAsyncTask(
        private val database: AppDatabaseNotes?,
        private val selectedItem: Note
    ) : AsyncTask<Void, Void, Unit>() {

        override fun doInBackground(vararg params: Void): Unit? {
            return database?.notesDao()?.deleteNote(selectedItem)
        }
    } */

}