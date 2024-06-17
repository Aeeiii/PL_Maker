package com.practicum.pl_maker

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class TrackHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val trackIcon: ImageView = itemView.findViewById(R.id.track_icon)
    private val trackName: TextView = itemView.findViewById(R.id.track_name)
    private val musicianName: TextView = itemView.findViewById(R.id.musician_name)
    private val trackTiming: TextView = itemView.findViewById(R.id.track_timing)

    fun bind(model: Track) {
        trackName.text = model.trackName
        musicianName.text = model.musicianName
        trackTiming.text = model.trackTime
        val imageUrl = model.artworkUrl100

        Glide.with(itemView).load(imageUrl)
            .centerCrop()
            .transform(RoundedCorners(10))
            .placeholder(R.drawable.dura)
            .into(trackIcon)

    }

}