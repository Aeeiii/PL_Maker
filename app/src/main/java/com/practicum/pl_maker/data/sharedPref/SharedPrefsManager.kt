package com.practicum.pl_maker.data.sharedPref

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practicum.pl_maker.data.SavedDataClient
import com.practicum.pl_maker.data.dto.SharedPrefsHistory
import com.practicum.pl_maker.data.dto.TrackDto


class SharedPrefsManager(context: Context) : SavedDataClient {
    private val sharedPreferences = context.getSharedPreferences(HISTORY_KEY, Context.MODE_PRIVATE)
    private var savesTracks = ArrayList<TrackDto>()


    override fun getSaved(): SharedPrefsHistory {
        val json =
            sharedPreferences.getString(HISTORY_KEY, null) ?: return SharedPrefsHistory(ArrayList())
        savesTracks = if (json != "") {
            Gson().fromJson<ArrayList<TrackDto>>(
                json,
                object : TypeToken<ArrayList<TrackDto>>() {}.type
            )
        } else {
            ArrayList<TrackDto>()
        }
        return SharedPrefsHistory(savesTracks)
    }

    override fun save(dto: Any) {
        if (dto is TrackDto) {
            val tracks = getSaved()

            val index: Int = tracks.savedTracks.indexOf(dto)

            if (index >= 0) tracks.savedTracks.removeAt(index)

            tracks.savedTracks.add(0, dto)

            if (tracks.savedTracks.size > 10) tracks.savedTracks.removeAt(10)

            val json = Gson().toJson(tracks.savedTracks)
            sharedPreferences
                .edit()
                .putString(HISTORY_KEY, json)
                .apply()
            savesTracks = tracks.savedTracks

        }
    }

    override fun clean() {
        savesTracks.clear()
        sharedPreferences
            .edit()
            .putString(HISTORY_KEY, "")
            .apply()
    }

    private companion object {
        const val HISTORY_KEY = "key"
    }
}