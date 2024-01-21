package com.example.taskmanagerapp.di.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.taskmanagerapp.data.external.ExternalTasksSource
import com.example.taskmanagerapp.di.annotations.ApplicationDataStore
import com.example.taskmanagerapp.di.annotations.ApplicationScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides

@Module
interface ExternalSourceModule {

    companion object {

        @ApplicationScope
        @Provides
        fun provideGson(): Gson {
            return GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create()
        }

        @ApplicationScope
        @Provides
        fun provideApplicationDataStore(
            @ApplicationDataStore dataStoreName: String,
            context: Context,
        ): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create(
                produceFile = { context.preferencesDataStoreFile(dataStoreName) },
                corruptionHandler = ReplaceFileCorruptionHandler { emptyPreferences() }
            )
        }

        @ApplicationScope
        @Provides
        fun provideExternalSourceManager(
            dataStore: DataStore<Preferences>,
            gson: Gson,
        ): ExternalTasksSource {
            return ExternalTasksSource(dataStore, gson)
        }
    }
}