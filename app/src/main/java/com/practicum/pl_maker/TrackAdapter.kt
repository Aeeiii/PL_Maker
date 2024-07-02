package com.practicum.pl_maker

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TrackAdapter(
    private val track: List<Track>,
    private val searchHistory: SearchHistory
) : RecyclerView.Adapter<TrackHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder =
        TrackHolder(parent, searchHistory)


    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        holder.bind(track[position])
    }

    override fun getItemCount(): Int {
        return track.size
    }

}