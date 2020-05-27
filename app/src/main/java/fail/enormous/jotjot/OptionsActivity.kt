package fail.enormous.jotjot

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OptionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        val englishText: TextView = findViewById(R.id.english) as TextView
        englishText.setOnClickListener {
            englishText.text = getString(R.string.english)
        }

    }
}
