package com.marcosnathan.ownvault.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marcosnathan.ownvault.ui.theme.OwnVaultTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewFolderDialog(
    folderName: String,
    onFolderNameChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onDone: (String) -> Unit,
) {
    BasicAlertDialog(onDismissRequest = onDismissRequest) {
        CreateNewFolderDialogContent(
            folderName = folderName,
            onFolderNameChange = onFolderNameChange,
            onDone = onDone,
            onDismissRequest = onDismissRequest
        )
    }
}

@Composable
private fun CreateNewFolderDialogContent(
    folderName: String,
    onDismissRequest: () -> Unit,
    onFolderNameChange: (String) -> Unit,
    onDone: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .padding(24.dp)
        ) {
            Text(
                text = "New Folder",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = folderName,
                onValueChange = onFolderNameChange,
                singleLine = true,
                label = {
                    Text(text = "Folder name")
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = onDismissRequest
                ) {
                    Text(
                        text = "Cancel"
                    )
                }
                TextButton(
                    onClick = {
                        onDone(folderName)
                    },
                    enabled = folderName.isNotBlank()
                ) {
                    Text(
                        text = "Done"
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun CreateNewFolderDialogContentPreview() {
    OwnVaultTheme{
        CreateNewFolderDialogContent(
            folderName = "folder 1",
            onFolderNameChange = {},
            onDone = {},
            onDismissRequest = {}
        )
    }
}