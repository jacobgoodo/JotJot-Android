package fail.enormous.jotjot

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var backCounter = 0 // set backCounter to 0, used in onBackPressed()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // Force portrait mode

        val notesButton = findViewById<Button>(R.id.mainNotesButton)
        notesButton.setOnClickListener {
            val showNotesActivity = Intent(
                this,
                NotesActivity::class.java
            ) // showNotesActivity = Intent, run NotesActivity.kt

            startActivity(showNotesActivity) // Start the notes page (showNotesActivity)
        }

        val remindersButton = findViewById<Button>(R.id.mainRemindersButton)
        remindersButton.setOnClickListener {
            val showRemindersActivity = Intent(
                this,
                RemindersActivity::class.java
            ) // showNotesActivity = Intent, run RemindersActivity.kt

            startActivity(showRemindersActivity) // Start the notes page (showRemindersActivity)
        }

        val listsButton = findViewById<Button>(R.id.mainListsButton)
        listsButton.setOnClickListener {
            val showListsActivity = Intent(this, ListsActivity::class.java)

            startActivity(showListsActivity) // Start lists activity
        }

        val optionsButton = findViewById<ImageButton>(R.id.optionButton)
        optionsButton.setOnClickListener {
            val showOptionsActivity = Intent(this, OptionsActivity::class.java)
            startActivity(showOptionsActivity)
        }

    }

    // Run goHome() when back is pressed 3 times TODO: Fix delay of Toast messages, commented out because they were weird
    override fun onBackPressed() {
        if (backCounter < 2) {
            this.backCounter += 1
            // Debugging for when writing this function
            Log.d("onBackPressed","backCounter is $backCounter")
            var backtimes = 2 - backCounter
            // Make a Toast message. In en: Press back button %1$s more times to exit.
            //Toast.makeText(applicationContext, getString(R.string.back_three_times_plural, backtimes), Toast.LENGTH_SHORT).show()
        }

        // The same as the previous if statement, but allows for languages that specify plural. In this case - singular form
        // For instance, Japanese isn't affected. This method allows for different languages to be easily added.
        if (backCounter < 3) {
            this.backCounter += 1
            // Debugging for when writing this function
            Log.d("onBackPressed","backCounter is $backCounter")
            var backtimes = 2 - backCounter
            // Make a Toast message. In en: Press back button %1$ more time to exit.
            //Toast.makeText(applicationContext, getString(R.string.back_three_times, backtimes), Toast.LENGTH_SHORT).show()
        }
        if (backCounter == 3) {
            Log.d("onBackPressed","backCounter is $backCounter")
            // Go to home screen
            goHome()
        }
    }

    // Go to home screen
    private fun goHome() {
        val gohome = Intent(Intent.ACTION_MAIN)
        gohome.addCategory(Intent.CATEGORY_HOME)
        gohome.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        // Reset back counter
        backCounter = 0
        // Run the gohome Intent -> home screen
        startActivity(gohome)
    }
}
