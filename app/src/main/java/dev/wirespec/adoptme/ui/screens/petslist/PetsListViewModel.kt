package dev.wirespec.adoptme.ui.screens.petslist

import androidx.compose.foundation.ScrollState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.wirespec.adoptme.da.paging.PetsPagingDataSource
import dev.wirespec.adoptme.da.web.PetAPIOptions
import dev.wirespec.adoptme.models.PetListItemInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PetsListViewModel: ViewModel() {

    private val apiOptions = PetAPIOptions()

    var scrollState: ScrollState = ScrollState(0)

    init {
        viewModelScope.launch {
        }
    }

    val pets: Flow<PagingData<PetListItemInfo>> = Pager(PagingConfig(pageSize = 60)) {
        PetsPagingDataSource(apiOptions)
    }.flow.cachedIn(viewModelScope)
}