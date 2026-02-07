package com.sample.bssdemo.Utils

sealed class UiState<out T> {
    data class Success<T>(val data: T): UiState<T>()
    data class Failure<T>(val error: String): UiState<T>()
    object Loading: UiState<Nothing>()
}