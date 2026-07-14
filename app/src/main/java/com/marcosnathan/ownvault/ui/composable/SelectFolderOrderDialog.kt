package com.marcosnathan.ownvault.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.marcosnathan.ownvault.data.FolderOrder
import com.marcosnathan.ownvault.model.Folder
import com.marcosnathan.ownvault.ui.theme.OwnVaultTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectFolderOrderDialog(
    selectedOption: FolderOrder,
    onDismissRequest: () -> Unit,
    onOptionSelected: (FolderOrder) -> Unit,
) {
    BasicAlertDialog(onDismissRequest = onDismissRequest){
        SelectFolderOrderDialogContent(
            onOptionSelected = onOptionSelected,
            selectedOption = selectedOption
        )
    }
}

@Composable
private fun SelectFolderOrderDialogContent(
    selectedOption: FolderOrder,
    onOptionSelected: (FolderOrder) -> Unit,
    modifier: Modifier = Modifier
) {
    val radioButtons = listOf(
        FolderOrder.NAME,
        FolderOrder.DATE,
    )
    Card(
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .padding(vertical = 24.dp)
                .selectableGroup()

            ,
        ) {
            Text(
                text = "Ordenar por",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Column{
                radioButtons.forEach { order ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = selectedOption == order,
                                role = Role.RadioButton,
                                onClick = {
                                    onOptionSelected(order)
                                }
                            ).padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            RadioButton(
                                selected = order == selectedOption,
                                onClick = null
                            )

                            Spacer(Modifier.width(8.dp))

                            Text(
                                text = when (order) {
                                    FolderOrder.NAME -> "Nome"
                                    FolderOrder.DATE -> "Data"
                                },
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun SelectFolderOrderDialogPreview() {
    SelectFolderOrderDialogContent(
        selectedOption = FolderOrder.NAME,
        onOptionSelected = {},
    )
}