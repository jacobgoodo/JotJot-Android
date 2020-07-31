package fail.enormous.jotjot

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class SecretActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secret)

        val browser = findViewById<WebView>(R.id.hh)
        val showMainActivity = Intent(this, MainActivity::class.java)
        browser.loadUrl("https://www.youtube.com/watch?v=WXxV9g7lsFE&t=9s") // Shawty's got a melody in my head
        startActivity(showMainActivity)
    }
}