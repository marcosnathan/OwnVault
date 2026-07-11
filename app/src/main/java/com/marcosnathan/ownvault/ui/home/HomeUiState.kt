package com.marcosnathan.ownvault.ui.home

import com.marcosnathan.ownvault.data.FileOrder
import com.marcosnathan.ownvault.data.FolderOrder
import com.marcosnathan.ownvault.model.Folder


data class FilterMenuItem(
    val selected: Boolean,
    val type: FolderOrder
)

data class HomeUiState(
    val folders: List<Folder> = emptyList(),
    val selectedFolderOrder: FolderOrder = FolderOrder.NAME,
    val menuItems: List<FilterMenuItem> = listOf(
        FilterMenuItem(
            selected = true,
            type = FolderOrder.NAME
        ),
        FilterMenuItem(
            selected = false,
            type = FolderOrder.DATE
        ),
        FilterMenuItem(
            selected = false,
            type = FolderOrder.SIZE
        ),
    ),
    val isLoading: Boolean = true,
    val error: String? = null
)
