package com.practicum.pl_maker.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.practicum.pl_maker.R
import com.practicum.pl_maker.ui.media.MediaActivity
import com.practicum.pl_maker.ui.settings.SettingsActivity
import com.practicum.pl_maker.ui.tracklist.SearchActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val buttonSearch = findViewById<Button>(R.id.button_search)
        val buttonMedia = findViewById<Button>(R.id.button_media)
        val buttonSettings = findViewById<Button>(R.id.button_settings)

        buttonSearch.setOnClickListener {
            val displayIntent = Intent(this, SearchActivity::class.java)
            startActivity(displayIntent)
        }


        val displayIntentNew = Intent(this, MediaActivity::class.java)
        val buttonMediaClickListener: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                startActivity(displayIntentNew)
            }
        }
        buttonMedia.setOnClickListener(buttonMediaClickListener)

        buttonSettings.setOnClickListener {
            val displayIntent = Intent(this, SettingsActivity::class.java)
            startActivity(displayIntent)
        }
    }
}