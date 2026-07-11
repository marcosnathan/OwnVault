package com.marcosnathan.ownvault.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.marcosnathan.ownvault.data.FolderOrder
import com.marcosnathan.ownvault.model.Folder
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeContent(
        uiState = uiState,
        onSortFolder = viewModel::changeSort,
        onCreateFolder = viewModel::createFolder,
        modifier = modifier
    )
}

@Composable
private fun HomeContent(
    uiState: HomeUiState,
    onSortFolder: (FolderOrder) -> Unit,
    onCreateFolder: (Folder) -> Unit,
    modifier: Modifier = Modifier
) {
    Log.i("HomeContent", "HomeContent: ${uiState.folders.map { it.name.plus(" | ").plus(it.createdAt) }}")

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = {
                onSortFolder(FolderOrder.NAME)
            }
        ) {
            Text("Sort by name")
        }
        Button(
            onClick = {
                onSortFolder(FolderOrder.SIZE)
            }
        ) {
            Text("Sort by size")
        }
        Button(
            onClick = {
                onSortFolder(FolderOrder.DATE)
            }
        ) {
            Text("Sort by date")
        }
    }
}