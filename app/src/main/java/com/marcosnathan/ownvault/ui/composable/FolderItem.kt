package com.marcosnathan.ownvault.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterNone
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marcosnathan.ownvault.model.Folder
import com.marcosnathan.ownvault.ui.theme.OwnVaultTheme

@Composable
fun FolderItem(
    folder: Folder,
    onFolderClick: (Folder) -> Unit,
    modifier: Modifier = Modifier,
    isOnGrid: Boolean = true,
) {
    if (isOnGrid){
        FolderGridItem(
            folder = folder,
            onFolderClick = onFolderClick,
            modifier = modifier
        )
    } else {
        FolderListItem(
            folder = folder,
            onFolderClick = onFolderClick,
            modifier = modifier
        )
    }
}

@Composable
private fun FolderGridItem(
    folder: Folder,
    onFolderClick: (Folder) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .clickable(
                    enabled = true
                ){
                    onFolderClick(folder)
                }
                .padding(50.dp)
                .aspectRatio(1.5f)
        ){
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
            )
        }
        Text(
            text = folder.name,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun FolderListItem(
    folder: Folder,
    onFolderClick: (Folder) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .clickable {
                onFolderClick(folder)
            }
            .padding(16.dp)
            .fillMaxWidth()

    ) {
        Icon(
            imageVector = Icons.Default.Folder,
            contentDescription = null
        )
        Text(
            text = folder.name
        )
        if (folder.isProtected){
            Spacer(Modifier.weight(1F))
            Icon(
                imageVector = Icons.Default.Lock,

                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FolderListItemPreview() {
    OwnVaultTheme {
        FolderListItem(
            folder = Folder(name = "Main Folder", isProtected = true),
            onFolderClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FolderGridItemPreview() {
    OwnVaultTheme {
        FolderGridItem(
            folder = Folder(name = "Main Folder", isProtected = true),
            onFolderClick = {}
        )
    }
}