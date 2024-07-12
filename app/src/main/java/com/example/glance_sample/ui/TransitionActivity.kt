package com.example.glance_sample.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.glance_sample.ui.theme.Glance_sampleTheme

class TransitionActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Glance_sampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Text(
                        text = intent.getStringExtra(KEY_CURRENT_TIME) ?: "No time",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    companion object {
        private const val KEY_CURRENT_TIME = "key_current_time"
        fun createIntent(context: Context, currentTime: String): Intent {
            return Intent(context, TransitionActivity::class.java).apply {
                putExtra(KEY_CURRENT_TIME, currentTime)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        }
    }
}
