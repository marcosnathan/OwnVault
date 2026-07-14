package com.marcosnathan.ownvault.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object HomeDestination: NavKey

@Serializable
data class FolderDestination(
    val id: Long
) : NavKey

