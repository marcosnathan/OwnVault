package com.marcosnathan.ownvault.ui.home

import app.cash.turbine.test
import com.marcosnathan.ownvault.data.datasource.local.database.model.asExternalModel
import com.marcosnathan.ownvault.fake.FakeFolderRepository
import com.marcosnathan.ownvault.fake.datasource.FakeRoomDataSource
import com.marcosnathan.ownvault.rules.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    private val testRule = MainDispatcherRule()

    @Test
    fun homeViewModel_getFolders_stateHasFoldersOrEmpty() = runTest {
        val viewModel = HomeViewModel(
            folderRepository = FakeFolderRepository()
        )
        viewModel.uiState.test {
            assertEquals(
                HomeUiState(),
                awaitItem()
            )
            assertEquals(
                HomeUiState(
                    isLoading = false,
                    folders = FakeRoomDataSource.fakeFolders.map { it.asExternalModel() }
                ),
                awaitItem()
            )
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun homeViewModel_createFolder_stateShowTheNewFolder() = runTest {
        val viewModel = HomeViewModel(
            folderRepository = FakeFolderRepository()
        )
        viewModel.uiState.test {
            awaitItem()
            assertEquals(
                3,
                awaitItem().folders.size
            )
            viewModel.executeIntent(HomeUserIntent.CreateFolder("Folder 4"))
            assertEquals(
                4,
                awaitItem().folders.size
            )
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun homeViewModel_deleteFolders_stateFoldersAreEmpty() = runTest {
       val viewModel = HomeViewModel(
           folderRepository = FakeFolderRepository()
       )
        viewModel.uiState.test {
            awaitItem()
            val folders = awaitItem().folders
            viewModel.executeIntent(HomeUserIntent.DeleteFolders(folders.map { it.id }))
            assertTrue(awaitItem().folders.isEmpty())
        }
    }

    @Test
    fun homeViewModel_createDuplicatedFolder_stateShowErrorMessage() = runTest {
        val viewModel = HomeViewModel(
            folderRepository = FakeFolderRepository()
        )
        viewModel.uiState.test {
            awaitItem()
            awaitItem()
            viewModel.executeIntent(HomeUserIntent.CreateFolder("Folder 1"))
            viewModel.uiEvents.test {
                assertTrue(awaitItem() is HomeUiEvent.Error)
            }
        }
    }
}