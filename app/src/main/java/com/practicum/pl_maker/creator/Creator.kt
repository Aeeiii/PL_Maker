package com.practicum.pl_maker.creator

import android.content.SharedPreferences
import com.practicum.pl_maker.data.network.RetrofitNetworkClient
import com.practicum.pl_maker.data.network.TrackRepositoryImpl
import com.practicum.pl_maker.data.sharedPref.SettingsManager
import com.practicum.pl_maker.data.sharedPref.SettingsSharedPrefsImpl
import com.practicum.pl_maker.data.sharedPref.SharedPrefsManager
import com.practicum.pl_maker.data.sharedPref.TrackSharedPrefsImpl
import com.practicum.pl_maker.domain.api.SettingsInteractor
import com.practicum.pl_maker.domain.api.SettingsSharedPref
import com.practicum.pl_maker.domain.api.TrackRepository
import com.practicum.pl_maker.domain.api.TrackSharedPref
import com.practicum.pl_maker.domain.api.TracksInteractor
import com.practicum.pl_maker.domain.impl.SettingsInteractorImpl
import com.practicum.pl_maker.domain.impl.TrackInteractorImpl

object Creator {
    private fun getTracksRepository(): TrackRepository {
        return TrackRepositoryImpl(RetrofitNetworkClient())
    }

    private fun getTrackSharedPrefs(sharedPreferences: SharedPreferences): TrackSharedPref {
        return TrackSharedPrefsImpl(SharedPrefsManager(sharedPreferences))
    }

    private fun getSettingsSharedPrefs(sharedPreferences: SharedPreferences): SettingsSharedPref {
        return SettingsSharedPrefsImpl(SettingsManager(sharedPreferences))
    }

    fun provideTracksInteractor(sharedPreferences: SharedPreferences): TracksInteractor {
        return TrackInteractorImpl(getTracksRepository(), getTrackSharedPrefs(sharedPreferences))
    }

    fun provideSettingsInteractor(sharedPreferences: SharedPreferences): SettingsInteractor {
        return SettingsInteractorImpl(getSettingsSharedPrefs(sharedPreferences))
    }

}