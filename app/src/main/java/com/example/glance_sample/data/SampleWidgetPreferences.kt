package com.example.glance_sample.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private const val DATASTORE_NAME = "SampleWidget"

val Context.dataStore by preferencesDataStore(
    name = DATASTORE_NAME
)

object SampleWidgetPreferences {
    private val KEY_USER_IMAGE_URL = stringPreferencesKey("key_user_image_url")
    private val KEY_USER_NAME = stringPreferencesKey("key_user_name")

    suspend fun getUserImageUrl(context: Context): String? {
        return context.dataStore.data.first()[KEY_USER_IMAGE_URL]
    }

    suspend fun setUserImageUrl(context: Context, value: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_USER_IMAGE_URL] = value
        }
    }

    suspend fun getUserName(context: Context): String? {
        return context.dataStore.data.first()[KEY_USER_NAME]
    }

    suspend fun setUserName(context: Context, value: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_USER_NAME] = value
        }
    }
}
