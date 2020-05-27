package fail.enormous.jotjot

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // Force portrait mode

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

            startActivity(showListsActivity) // Start lists activity
        }

        val optionsButton = findViewById<ImageButton>(R.id.optionButton)
        optionsButton.setOnClickListener{
            val showOptionsActivity = Intent(this, OptionsActivity::class.java)
            startActivity(showOptionsActivity)
        }

    }
}
