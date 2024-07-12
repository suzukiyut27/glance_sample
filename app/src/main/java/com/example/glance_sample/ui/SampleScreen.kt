package com.example.glance_sample.ui

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.clickable
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.ContentScale
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.size
import androidx.glance.text.Text
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SampleScreen(
    context: Context,
    userName: String,
    url: String,
    current: String
) {
    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }
    val scope = rememberCoroutineScope()
    Column(
        modifier = GlanceModifier.fillMaxSize()
            .background(color = Color.White)
            .clickable(
                onClick = actionRunCallback<LaunchActivityAction>(
                    parameters = actionParametersOf(
                        ActionParameters.Key<String>(LaunchActivityAction.KEY_CURRENT_TIME) to System.currentTimeMillis()
                            .toString()
                    )
                )
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (bitmap.value == null) {
            SideEffect {
                val imageLoader = context.imageLoader
                val request = ImageRequest.Builder(context).data(url).build()
                scope.launch(Dispatchers.IO) {
                    val result = imageLoader.execute(request)
                    if (result is SuccessResult) {
                        bitmap.value = result.drawable.toBitmapOrNull()
                    }
                }
            }
        } else {
            Image(
                provider = ImageProvider(bitmap.value!!),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = GlanceModifier.size(56.dp)
            )
            Text(
                text = userName
            )
            Spacer(modifier = GlanceModifier.size(16.dp))
            Text(text = current)
        }
    }
}
