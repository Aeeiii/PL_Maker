package com.practicum.pl_maker

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchHistory(
    private val sharedPreferences: SharedPreferences
) {
    var savesTracks = ArrayList<Track>()

    fun getSavedTracks(): ArrayList<Track> {
        val json = sharedPreferences.getString(HISTORY_KEY, null) ?: return ArrayList()
        savesTracks = if (json != "") {
            Gson().fromJson<ArrayList<Track>>(json, object : TypeToken<ArrayList<Track>>() {}.type)
        } else {
            ArrayList<Track>()
        }
        return savesTracks
    }

    fun saveTrack(track: Track) {
        val tracks = getSavedTracks()

        val index: Int = tracks.indexOf(track)

        if (index >= 0) tracks.removeAt(index)


        tracks.add(0, track)

        if (tracks.size > 10) tracks.removeAt(10)

        val json = Gson().toJson(tracks)
        sharedPreferences
            .edit()
            .putString(HISTORY_KEY, json)
            .apply()
        savesTracks = tracks
    }

    fun cleanHistory() {
        savesTracks.clear()
        sharedPreferences
            .edit()
            .putString(HISTORY_KEY, "")
            .apply()
    }

    companion object {
        const val HISTORY_KEY = "key"
    }


}