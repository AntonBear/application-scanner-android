package com.holzed.applicationscanner.ui.appdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.holzed.applicationscanner.R
import com.holzed.applicationscanner.data.HashRepository
import com.holzed.applicationscanner.data.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppDetailViewModel @Inject constructor(
    private val resourcesProvider: ResourcesProvider,
    private val hashRepository: HashRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<AppDetailState>(AppDetailState.Loading)
    val state: StateFlow<AppDetailState> = _state

    fun loadDetails(
        title: String,
        version: String,
        packageName: String,
        packagePath: String,
    ) {
        viewModelScope.launch {
            val hash = hashRepository.getHash(packageName, packagePath)
            _state.value = AppDetailState.Loaded(
                title = title,
                version = resourcesProvider.getString(R.string.version_prefix, version),
                packageName = packageName,
                hash = resourcesProvider.getString(R.string.hash_prefix, hash),
            )
        }
    }
}
