package com.marcosnathan.ownvault.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcosnathan.ownvault.data.FolderOrder
import com.marcosnathan.ownvault.data.FolderRepository
import com.marcosnathan.ownvault.model.Folder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val folderRepository: FolderRepository
) : ViewModel() {
    private val folderOrder = MutableStateFlow(FolderOrder.NAME)
    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState = folderOrder
        .flatMapLatest(folderRepository::getAll)
        .map { folders ->
            HomeUiState(
                folders = folders,
                selectedFolderOrder = folderOrder.value,
                isLoading = false
            )
        }
        .catch { throwable ->
            emit(
                HomeUiState(
                    error = throwable.message ?: "Failed to fetch folders"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = HomeUiState()
        )

    private val internalUiEvents = Channel<HomeUiEvent>()
    val uiEvents = internalUiEvents.receiveAsFlow()

    fun executeIntent(intent: HomeUserIntent) {
        when (intent) {
            is HomeUserIntent.CreateFolder -> handleCreateFolder(intent.name)
            is HomeUserIntent.DeleteFolders -> handleDeleteFolders(intent.ids)
            is HomeUserIntent.SortFolder -> handleSortFolders(intent.order)
        }
    }

    private fun handleCreateFolder(name: String) {
        viewModelScope.launch {
            try {
                folderRepository.insert(
                    Folder(
                        name = name
                    )
                )
            } catch (e: Exception){
                Log.i(HomeViewModel::class.simpleName, "handleCreateFolder: $e")
            }
        }
    }

    private fun handleDeleteFolders(ids: List<Long>) {
        viewModelScope.launch {
            folderRepository.deleteFolders(ids)
        }
    }

    private fun handleSortFolders(order: FolderOrder) {
        folderOrder.update { order }
    }
}