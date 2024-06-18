package com.practicum.pl_maker

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.util.Locale

class TrackHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.track, parent, false)
    ) {

    private val trackIcon: ImageView = itemView.findViewById(R.id.track_icon)
    private val trackName: TextView = itemView.findViewById(R.id.track_name)
    private val musicianName: TextView = itemView.findViewById(R.id.musician_name)
    private val trackTiming: TextView = itemView.findViewById(R.id.track_timing)

    fun bind(model: Track) {
        trackName.text = model.trackName
        musicianName.text = model.artistName
        trackTiming.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(model.trackTimeMillis.toInt())

        val imageUrl = model.artworkUrl100

        Glide.with(itemView).load(imageUrl)
            .centerCrop()
            .transform(RoundedCorners(2))
            .placeholder(R.drawable.dura)
            .into(trackIcon)

    }

}