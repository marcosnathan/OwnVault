package com.marcosnathan.ownvault.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcosnathan.ownvault.data.FolderOrder
import com.marcosnathan.ownvault.data.FolderRepository
import com.marcosnathan.ownvault.model.Folder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val folderRepository: FolderRepository
) : ViewModel() {

    private var _sort = MutableStateFlow(FolderOrder.NAME)
    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState = _sort.flatMapLatest(folderRepository::getAll)
        .map {
            HomeUiState(
                folders = it,
                isLoading = false,
                selectedFolderOrder = _sort.value
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = HomeUiState()
        )

    fun changeSort(sort: FolderOrder){
        _sort.update { sort }
    }

    fun createFolder(folder: Folder) {
        viewModelScope.launch {
            folderRepository.insert(folder)
        }
    }
}