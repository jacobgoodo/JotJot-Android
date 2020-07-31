package fail.enormous.jotjot

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var backCounter = 0 // set backCounter to 0, used in onBackPressed() to call goHome()
    private var tapCounter = 0 // Tap of the JotJot icon

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resetBackCount() // Reset backCounter to 0, if it isn't already
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

            startActivity(showRemindersActivity) // Start the reminders page (showRemindersActivity)
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
            // Show the options menu by pressing the button.
        }

    }

    // Run goHome() when back is pressed 3 times
    // TODO: Potentially change the colour of the Toast messages. Requires custom Toasts
    @SuppressLint("StringFormatMatches") // Pesky Android Studio syntax error suppression -> the app works fine this way
    override fun onBackPressed() {
        if (backCounter < 3) {
            this.backCounter += 1
            // Debugging for when writing this function
            Log.d("onBackPressed","backCounter is $backCounter")
            val backtimes = 3 - backCounter // val as the way backtimes is defined doesn't change -- the variable backCounter changes which is defined in the val

            if (backtimes == 2) {
                // Make a Toast message. In en: Press back button %1$s more times to exit.
                Toast.makeText(
                    applicationContext,
                    getString(R.string.back_three_times_plural, backtimes),
                    Toast.LENGTH_SHORT
                ).show()
            }

            if (backtimes == 1) {
                // The same as the previous if statement, but allows for languages that specify plural. In this case - singular form
                // For instance, Japanese isn't affected. This method allows for different languages to be easily added.
                // Make a Toast message. In en: Press back button %1$ more time to exit.
                Toast.makeText(
                    applicationContext,
                    getString(R.string.back_three_times, backtimes),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        if (backCounter == 3) {
            // Console debugging
            Log.d("onBackPressed","backCounter is $backCounter")
            // Go to home screen
            goHome()
        }
    }

    // Go to home screen function
    private fun goHome() {
        val gohome = Intent(Intent.ACTION_MAIN)
        gohome.addCategory(Intent.CATEGORY_HOME)
        gohome.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        // Reset back counter
        resetBackCount()
        // Run the gohome Intent -> home screen
        startActivity(gohome)
    }

    private fun resetBackCount() {
        backCounter = 0
        tapCounter = 0
    }

    // This is the super-secret menu
    fun LogoPress(view: View) {
        tapCounter += 1
        if (tapCounter == 10) {
            val showSecretActivity = Intent(this, SecretActivity::class.java)
            // Reset counters
            resetBackCount()
            // Run the activity
            startActivity(showSecretActivity)
        }
    }
}