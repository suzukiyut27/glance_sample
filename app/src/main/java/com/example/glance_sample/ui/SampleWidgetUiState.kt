package com.example.glance_sample.ui

sealed interface SampleWidgetUiState {
    data object Loading: SampleWidgetUiState
    data class Success(val userName: String, val userImageUrl: String, val current: String): SampleWidgetUiState
    data class Error(val message: String): SampleWidgetUiState
}
