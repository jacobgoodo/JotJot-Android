package fail.enormous.jotjot

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

/*
Note on this activity!
This is NOT meant to be a useful feature, hence it is very hidden (requires tapping the JotJot icon
in MainActivity 10 times in order to open it). It was simply added for a bit of humour.
- Jacob
 */

class SecretActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secret)

        val browser = findViewById<WebView>(R.id.hh)
        val showMainActivity = Intent(this, MainActivity::class.java)
        browser.loadUrl("https://www.youtube.com/watch?v=WXxV9g7lsFE&t=10s") // Shawty's got a melody in my head
        // Quickly load MainAcivity before YouTube is opened
        startActivity(showMainActivity)
    }

}