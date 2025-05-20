package com.holzed.applicationscanner.ui.appdetail

import androidx.lifecycle.ViewModel
import com.holzed.applicationscanner.R
import com.holzed.applicationscanner.data.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AppDetailViewModel @Inject constructor(
    private val resourcesProvider: ResourcesProvider,
) : ViewModel() {

    private val _uiState = MutableStateFlow<AppDetailUiState?>(null)

    val uiState: StateFlow<AppDetailUiState?> = _uiState

    fun setAppDetails(
        title: String,
        version: String,
        packageName: String,
        hash: String?,
    ) {
        _uiState.value = AppDetailUiState(
            title = title,
            version = resourcesProvider.getString(R.string.version_prefix, version),
            packageName = packageName,
            hash = resourcesProvider.getString(R.string.hash_prefix, hash ?: "-"),
        )
    }
}
