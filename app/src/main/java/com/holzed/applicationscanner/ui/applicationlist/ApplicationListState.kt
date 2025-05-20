package com.holzed.applicationscanner.ui.applicationlist

import com.holzed.applicationscanner.data.model.AppItemModel

sealed interface ApplicationListState {
    object Loading : ApplicationListState
    data class Loaded(val apps: List<AppItemModel>) : ApplicationListState
}
