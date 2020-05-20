package fail.enormous.jotjot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val notesButton = findViewById<Button>(R.id.mainNotesButton)
        notesButton.setOnClickListener {
            val showNotesActivity = Intent(this, NotesActivity::class.java) // showNotesActivity = Intent, run NotesActivity.kt

            startActivity(showNotesActivity) // Start the notes page (showNotesActivity)
        }

        val remindersButton = findViewById<Button>(R.id.mainRemindersButton)
        remindersButton.setOnClickListener {
            val showRemindersActivity = Intent(this, RemindersActivity::class.java) // showNotesActivity = Intent, run RemindersActivity.kt

            startActivity(showRemindersActivity) // Start the notes page (showRemindersActivity)
        }

        val listsButton = findViewById<Button>(R.id.mainListsButton)
        listsButton.setOnClickListener {
            val showListsActivity = Intent(this, ListsActivity::class.java)

            startActivity(showListsActivity)
        }

    }
}
