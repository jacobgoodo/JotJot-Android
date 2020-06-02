package fail.enormous.jotjot

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity


class OptionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        val browser = findViewById<WebView>(R.id.credWeb)
        browser.loadUrl("https://enormous.fail/JotJot/credits")

    }
}
