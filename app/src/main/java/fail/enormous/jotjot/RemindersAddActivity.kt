package fail.enormous.jotjot

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import java.util.*

class RemindersAddActivity : AppCompatActivity() {

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // Force portrait mode
        setContentView(R.layout.activity_reminders_add)

        val remCreateButton = findViewById<Button>(R.id.createButton) // Find createButton

        // ADD FUNCTIONS FOR SAVING DATA !!!

        remCreateButton.setOnClickListener {
            val showRemindersActivity = Intent(this, RemindersActivity::class.java)
            startActivity(showRemindersActivity) // Show reminders page
        }
    }

    // These two functions are called from button presses, due to  android:onClick in activity_reminders_add.xml
    fun showDatePickerDialog(view: View) {
        val datePick = DatePickerFragment()
        datePick.show(supportFragmentManager, "datePicker")

    }

    fun showTimePickerDialog(view: View) {
        TimePickerFragment().show(supportFragmentManager, "timePicker")
    }
}

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(activity, this, year, month, day)

    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        // Do something with the date chosen by the user
        // ADD FUNCTION TO ADD DATE TO DATABASE AND DISPLAY IT

    }
}


class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {

    }
}
