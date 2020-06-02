package fail.enormous.jotjot

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RemindersActivity : AppCompatActivity() {

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // Force portrait mode
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminders)

        val remAddButton = findViewById<FloatingActionButton>(R.id.remindersAddButton) // Reminders Floating Action Button
        remAddButton.setOnClickListener{
            val showRemindersAddActivity = Intent(this, RemindersAddActivity::class.java) // show RemindersAddActivity declaration
            startActivity(showRemindersAddActivity)
        }
    }
    override fun onBackPressed() {
        val showMainActivity = Intent(this, ListsActivity::class.java)
        startActivity(showMainActivity) // Go to MainActivity on back button press, rather than RemindersAddActivity
    }
}
