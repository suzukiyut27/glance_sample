package com.example.glance_sample.ui

import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.text.Text
import com.example.glance_sample.data.SampleWidgetPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SampleAppWidget : GlanceAppWidget() {
    private var uiState by mutableStateOf<SampleWidgetUiState>(SampleWidgetUiState.Loading)
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            when (val uiState = uiState) {
                SampleWidgetUiState.Loading -> {
                    LaunchedEffect(key1 = Unit) {
                        delay(3000)
                        updateUI(context)
                    }
                    Column(
                        modifier = GlanceModifier.fillMaxSize()
                            .background(color = Color.White),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is SampleWidgetUiState.Error -> {
                    Column(
                        modifier = GlanceModifier.fillMaxSize()
                            .background(color = Color.White),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = uiState.message)
                        Spacer(modifier = Modifier.size(16.dp))
                        Button(text = "Reload", onClick = { updateUI(context) })
                    }
                }

                is SampleWidgetUiState.Success -> {
                    SampleScreen(
                        context = context,
                        url = uiState.userImageUrl,
                        userName = uiState.userName,
                        current = uiState.current
                    )
                }
            }
        }
    }

    private fun updateUI(context: Context) {
        runBlocking {
            val userImageUrl = SampleWidgetPreferences.getUserImageUrl(context)
            val userName = SampleWidgetPreferences.getUserName(context)
            val current = SimpleDateFormat("MM/dd HH:mm:ss", Locale.getDefault()).format(Date())
            uiState = if (userImageUrl.isNullOrBlank() || userName.isNullOrBlank()) {
                SampleWidgetUiState.Error("User data not found")
            } else {
                SampleWidgetUiState.Success(userName, userImageUrl, current)
            }
        }
    }
}
