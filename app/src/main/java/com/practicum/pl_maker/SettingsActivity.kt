package com.practicum.pl_maker

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat

class SettingsActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val shareImage = findViewById<ImageView>(R.id.settings_image)
        val helpImage = findViewById<ImageView>(R.id.help_image)
        val licenseImage = findViewById<ImageView>(R.id.license_image)
        val themeSwitch = findViewById<SwitchCompat>(R.id.theme_switch)

        when (resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> themeSwitch.toggle()
        }

        themeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }


        shareImage.setOnClickListener {
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, applicationContext.getString(R.string.A_D_link))
                type = "text/plain"
                startActivity(Intent.createChooser(this, null))
            }
        }

        helpImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(
                Intent.EXTRA_EMAIL,
                arrayOf(applicationContext.getString(R.string.email_adress))
            )
            intent.putExtra(
                Intent.EXTRA_SUBJECT,
                applicationContext.getString(R.string.message_subject)
            )
            intent.putExtra(Intent.EXTRA_TEXT, applicationContext.getString(R.string.message_text))
            startActivity(intent)
        }

        licenseImage.setOnClickListener {
            val broseIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(applicationContext.getString(R.string.license_link))
            )
            startActivity(broseIntent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}