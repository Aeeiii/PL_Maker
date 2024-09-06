package com.practicum.pl_maker.creator

import android.content.Context
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

    private fun getTrackSharedPrefs(context: Context): TrackSharedPref {
        return TrackSharedPrefsImpl(SharedPrefsManager(context))
    }

    private fun getSettingsSharedPrefs(context: Context): SettingsSharedPref {
        return SettingsSharedPrefsImpl(SettingsManager(context))
    }

    fun provideTracksInteractor(context: Context): TracksInteractor {
        return TrackInteractorImpl(getTracksRepository(), getTrackSharedPrefs(context))
    }

    fun provideSettingsInteractor(context: Context): SettingsInteractor {
        return SettingsInteractorImpl(getSettingsSharedPrefs(context))
    }

}