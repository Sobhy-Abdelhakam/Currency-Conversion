package dev.sobhy.bmproject.ui.screens.composable

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Loading(isLoading: Boolean, modifier: Modifier) {
    if (!isLoading) return
    CircularProgressIndicator(modifier = modifier)
}