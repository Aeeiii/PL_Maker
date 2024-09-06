package com.practicum.pl_maker.ui.tracklist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.pl_maker.domain.models.Track

class TrackAdapter(
    private val track: List<Track>,
    private val listener: TrackHolder.Listener
) : RecyclerView.Adapter<TrackHolder>() {





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder =
        TrackHolder(parent)


    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        holder.bind(track[position], listener)
    }

    override fun getItemCount(): Int {
        return track.size
    }

}