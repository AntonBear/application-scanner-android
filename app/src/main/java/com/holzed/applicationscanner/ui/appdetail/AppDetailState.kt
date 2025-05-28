package com.holzed.applicationscanner.ui.appdetail

sealed interface AppDetailState {
    object Loading : AppDetailState
    data class Loaded(
        val title: String,
        val version: String,
        val packageName: String,
        val hash: String,
    ) : AppDetailState
}
