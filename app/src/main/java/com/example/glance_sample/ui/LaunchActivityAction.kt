package com.example.glance_sample.ui

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback

class LaunchActivityAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        val currentTime = parameters.getOrDefault(ActionParameters.Key(KEY_CURRENT_TIME), "")
        context.startActivity(
            TransitionActivity.createIntent(
                context,
                currentTime
            )
        )
    }

    companion object {
        const val KEY_CURRENT_TIME = "key_current_time"
    }
}
