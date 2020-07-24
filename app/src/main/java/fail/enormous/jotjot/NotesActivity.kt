package fail.enormous.jotjot

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // Force portrait mode
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        val notesAddButton = findViewById<FloatingActionButton>(R.id.notesAddButton) // Reminders Floating Action Button
        notesAddButton.setOnClickListener{
            val showNotesAddActivity = Intent(this, NotesAddActivity::class.java) // show RemindersAddActivity declaration
            startActivity(showNotesAddActivity)
        }
    }

    override fun onBackPressed() {
        val showMainActivity = Intent(this, MainActivity::class.java)
        startActivity(showMainActivity) // Go to MainActivity on back button press
    }
}
