package com.practicum.pl_maker

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson
import java.util.Locale

class PlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        val gson = Gson()
        val track = gson.fromJson(intent.getStringExtra("track"), Track::class.java)

        val trackIcon = findViewById<ImageView>(R.id.track_icon)
        val trackName = findViewById<TextView>(R.id.track_name)
        val musicianName = findViewById<TextView>(R.id.musician_name)
        val imageUrl = track.getCoverArtwork()
        val trackTiming = findViewById<TextView>(R.id.timing_track)
        val trackAlbum = findViewById<TextView>(R.id.album_track)
        val trackYear = findViewById<TextView>(R.id.year_track)
        val genre = findViewById<TextView>(R.id.genre_track)
        val country = findViewById<TextView>(R.id.country_track)

        trackName.text = track.trackName
        musicianName.text = track.artistName
        trackTiming.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis.toInt())
        trackAlbum.text = track.collectionName
        trackYear.text = track.getYear()
        genre.text = track.primaryGenreName
        country.text = track.country



        Glide.with(baseContext).load(imageUrl)
            .centerCrop()
            .transform(RoundedCorners(8))
            .placeholder(R.drawable.placeholder)
            .into(trackIcon)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}