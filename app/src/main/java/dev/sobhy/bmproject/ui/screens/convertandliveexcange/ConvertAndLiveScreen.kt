package dev.sobhy.bmproject.ui.screens.convertandliveexcange


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.sobhy.bmproject.ui.screens.composable.ErrorDialog
import dev.sobhy.bmproject.ui.screens.composable.Loading
import dev.sobhy.bmproject.ui.screens.convertandliveexcange.ConvertScreen.ConvertScreen
import dev.sobhy.bmproject.ui.screens.convertandliveexcange.ConvertScreen.ConvertViewModel
import dev.sobhy.bmproject.ui.screens.convertandliveexcange.liveexchangefeature.LiveExchangeScreen
import dev.sobhy.bmproject.ui.screens.convertandliveexcange.liveexchangefeature.LiveExchangeViewModel

@Composable
fun ConvertAndLiveScreen(
    convertViewModel: ConvertViewModel,
    viewModel: LiveExchangeViewModel,
) {
    val state = viewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .padding(horizontal = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Loading(isLoading = state.value.isLoading)
            ErrorDialog(
                shouldShow = state.value.dialogModel?.isShouldShow ?: false,
                message = state.value.dialogModel?.message ?: "",
                onDismiss = {
                    viewModel.dismissDialog()
                }
            )
        }
        ConvertScreen(viewModel = convertViewModel, viewModel)

        Divider(thickness = 1.dp, modifier = Modifier.padding(32.dp))

        LiveExchangeScreen(viewModel = viewModel)


    }
}