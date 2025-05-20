package com.holzed.applicationscanner.ui.applicationlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.holzed.applicationscanner.data.ApplicationListProvider
import com.holzed.applicationscanner.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplicationListViewModel @Inject constructor(
    private val appListProvider: ApplicationListProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _state = MutableStateFlow<ApplicationListState>(ApplicationListState.Loading)
    val state: StateFlow<ApplicationListState> = _state

    fun loadAppList() {
        viewModelScope.launch(ioDispatcher) {
            _state.value = ApplicationListState.Loading

            val apps = appListProvider.fetchApplicationInfo()
            _state.value = ApplicationListState.Loaded(apps)
        }
    }
}
