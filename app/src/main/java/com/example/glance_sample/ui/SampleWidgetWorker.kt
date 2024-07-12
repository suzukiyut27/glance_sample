package com.example.glance_sample.ui

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.glance_sample.api.GitHubClient
import com.example.glance_sample.data.SampleWidgetPreferences

class SampleWidgetWorker(
    private val context: Context,
    private val params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val appWidgetId = inputData.getInt(KEY_APP_WIDGET_ID, -1)
        if (appWidgetId == -1) {
            return Result.failure()
        }
        val glanceAppWidgetManager = GlanceAppWidgetManager(context)
        val glanceId = try {
            glanceAppWidgetManager.getGlanceIdBy(appWidgetId)
        } catch (e: IllegalArgumentException) {
            return Result.failure()
        }
        val client = GitHubClient()
        val user = client.getGitHubUser("suzukiyut27")
        val userImageUrl = user.avatarUrl
        val userName = user.name
        if (userImageUrl.isNotBlank() && !userName.isNullOrBlank()) {
            SampleWidgetPreferences.setUserImageUrl(context, userImageUrl)
            SampleWidgetPreferences.setUserName(context, userName)
            SampleAppWidget().update(context, glanceId)
            return Result.success()
        } else {
            return Result.failure()
        }
    }

    companion object {
        private const val KEY_APP_WIDGET_ID = "key_app_widget_id"
        fun request(
            context: Context,
            appWidgetIds: IntArray,
        ) {
            appWidgetIds.forEach { appWidgetId ->
                val request = OneTimeWorkRequestBuilder<SampleWidgetWorker>()
                    .setInputData(
                        workDataOf(
                            KEY_APP_WIDGET_ID to appWidgetId
                        )
                    )
                    .build()
                WorkManager.getInstance(context).enqueue(request)
            }
        }
    }
}
