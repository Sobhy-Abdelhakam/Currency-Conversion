package dev.sobhy.bmproject.ui.screens.composable

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun ErrorDialog(
    shouldShow: Boolean = false,
    message: String,
    onDismiss: () -> Unit,
) {
    if (!shouldShow) return
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = { onDismiss() }) {
                Text(text = "Close")
            }
        },
        title = { Text(text = "Error", fontSize = 20.sp) },
        text = { Text(text = message) }
    )
}