package fail.enormous.jotjot

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity


class OptionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        val browser = findViewById<WebView>(R.id.credWeb)
        // lazy credits because why not. BAD because requires internet and only English support TODO: offline and multilingual credits
        browser.loadUrl("https://enormous.fail/JotJot/credits")


    }
}
