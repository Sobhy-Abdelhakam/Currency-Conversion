package dev.sobhy.bmproject.ui.screens.composable

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import dev.sobhy.bmproject.R

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
                Text(text = stringResource(R.string.close))
            }
        },
        title = { Text(text = stringResource(R.string.error), fontSize = 20.sp) },
        text = { Text(text = message) }
    )
}