package com.marcosnathan.ownvault.ui.home

import com.marcosnathan.ownvault.data.FolderOrder
import com.marcosnathan.ownvault.model.Folder

data class HomeUiState(
    val folders: List<Folder> = emptyList(),
    val selectedFolderOrder: FolderOrder = FolderOrder.NAME,
    val isLoading: Boolean = true,
    val error: String? = null
)

sealed interface HomeUiEvent {
    data object FolderCreated: HomeUiEvent
    data object FoldersDeleted : HomeUiEvent
    data object FolderRenamed: HomeUiEvent
}

sealed interface HomeUserIntent {
    data class CreateFolder(val name: String) : HomeUserIntent
    data class DeleteFolders(val ids: List<Long>) : HomeUserIntent
    data class SortFolder(val order: FolderOrder) : HomeUserIntent
}
