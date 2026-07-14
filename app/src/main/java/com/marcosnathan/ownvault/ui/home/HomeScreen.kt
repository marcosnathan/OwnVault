package com.marcosnathan.ownvault.ui.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ViewList
import androidx.compose.material.icons.outlined.CreateNewFolder
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.marcosnathan.ownvault.R
import com.marcosnathan.ownvault.model.Folder
import com.marcosnathan.ownvault.ui.composable.CreateNewFolderDialog
import com.marcosnathan.ownvault.ui.composable.FolderItem
import com.marcosnathan.ownvault.ui.composable.SelectFolderOrderDialog
import com.marcosnathan.ownvault.ui.theme.OwnVaultTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var gridFolders by rememberSaveable {
        mutableStateOf(false)
    }
    HomeContent(
        uiState = uiState,
        gridFolders = gridFolders,
        onVisualChange = {
            gridFolders = !gridFolders
        },
        onAction = viewModel::executeIntent
    )
}



@SuppressLint("RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    uiState: HomeUiState,
    gridFolders: Boolean,
    onVisualChange: () -> Unit,
    onAction: (HomeUserIntent) -> Unit
) {
    val scrollBehavior =  TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    var showSortingDialog by rememberSaveable {
        mutableStateOf(false)
    }

    var showCreateFolderDialog by rememberSaveable {
        mutableStateOf(false)
    }

    var folderName by rememberSaveable {
        mutableStateOf("")
    }

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.home_topappbar_title),
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(
                        onClick = onVisualChange
                    ) {
                        AnimatedContent(
                            gridFolders,
                        ) {
                            Icon(
                                imageVector = if (it)
                                    Icons.AutoMirrored.Outlined.ViewList
                                else
                                    Icons.Outlined.GridView,
                                contentDescription = null
                            )
                        }

                    }
                    IconButton(
                        onClick = {
                            showSortingDialog = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.FilterList,
                            contentDescription = stringResource(R.string.home_topappbar_reorder_folders_action)
                        )
                    }
                    IconButton(
                        onClick = {
                            showCreateFolderDialog = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CreateNewFolder,
                            contentDescription = stringResource(R.string.home_topappbar_create_new_folder_action)
                        )
                    }

                }
            )
        }
    ) { innerPadding ->
        if (showSortingDialog){
            SelectFolderOrderDialog(
                selectedOption = uiState.selectedFolderOrder,
                onDismissRequest = {
                    showSortingDialog = false
                },
                onOptionSelected = {
                    onAction(HomeUserIntent.SortFolder(it))
                    showSortingDialog = false
                },
            )
        }
        if (showCreateFolderDialog){
            CreateNewFolderDialog(
                onDismissRequest = {
                    showCreateFolderDialog = false
                    folderName = ""

                },
                folderName = folderName,
                onFolderNameChange = {
                    folderName = it
                },
                onDone = {
                    onAction(HomeUserIntent.CreateFolder(folderName))
                    folderName = ""
                    showCreateFolderDialog = false
                }
            )
        }

        HomeFoldersContent(
            folders = uiState.folders,
            grid = gridFolders,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun HomeFoldersContent(
    folders: List<Folder>,
    grid: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = grid
    ) {
        if (it){
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 170.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier.padding(16.dp).fillMaxSize()
            ) {
                items(
                    items = folders,
                    key = { folder -> folder.id}
                ) { folder ->
                    FolderItem(
                        folder = folder,
                        onFolderClick = {},
                        isOnGrid = it,
                        modifier = Modifier.animateItem()
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = folders,
                    key = { folder -> folder.id}
                ) { folder ->
                    FolderItem(
                        folder = folder,
                        isOnGrid = it,
                        onFolderClick = {},
                        modifier = Modifier.animateItem()
                    )
                }
            }
        }
    }


}


@Preview
@Composable
private fun HomeScreenGridPreview() {
    OwnVaultTheme {
        HomeContent(
            uiState = HomeUiState(
                folders = listOf(
                    Folder(
                        id = 0L,
                        name = "Folder 1",
                        isProtected = false,
                    ),
                    Folder(
                        id = 1L,
                        name = "Folder 2",
                        isProtected = false,
                    ),
                    Folder(
                        id = 2L,
                        name = "Folder 3",
                        isProtected = true,
                    )
                )
            ),
            onAction = {},
            onVisualChange = {},
            gridFolders = true,
        )
    }
}

@Preview
@Composable
private fun HomeScreenListPreview() {
    OwnVaultTheme {
        HomeContent(
            uiState = HomeUiState(
                folders = listOf(
                    Folder(
                        id = 0L,
                        name = "Folder 1",
                        isProtected = false,
                    ),
                    Folder(
                        id = 1L,
                        name = "Folder 2",
                        isProtected = false,
                    ),
                    Folder(
                        id = 2L,
                        name = "Folder 3",
                        isProtected = true,
                    )
                )
            ),
            onAction = {},
            onVisualChange = {},
            gridFolders = false,
        )
    }
}