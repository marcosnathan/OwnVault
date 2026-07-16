package com.marcosnathan.ownvault.fake.datasource

import com.marcosnathan.ownvault.data.datasource.local.database.model.FolderEntity

object FakeRoomDataSource {
    val fakeFolders =
        listOf(
            FolderEntity(
                id = 1L,
                name = "Folder 1",
                password = "123"
            ),
            FolderEntity(
                id = 2L,
                name = "Folder 2",
            ),
            FolderEntity(
                id = 3L,
                name = "Folder 3",
            )
        )

}