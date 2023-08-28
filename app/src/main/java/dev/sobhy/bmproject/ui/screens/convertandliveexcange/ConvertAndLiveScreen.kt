package dev.sobhy.bmproject.ui.screens.convertandliveexcange


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.sobhy.bmproject.ui.screens.convertandliveexcange.ConvertScreen.ConvertScreen
import dev.sobhy.bmproject.ui.screens.convertandliveexcange.ConvertScreen.ConvertViewModel
import dev.sobhy.bmproject.ui.screens.convertandliveexcange.liveexchangefeature.LiveExchangeScreen
import dev.sobhy.bmproject.ui.screens.convertandliveexcange.liveexchangefeature.LiveExchangeViewModel

@Composable
fun ConvertAndLiveScreen(
    convertViewModel: ConvertViewModel,
    viewModel: LiveExchangeViewModel,
) {
   Column(modifier = Modifier
       .fillMaxSize()
       .verticalScroll(state = rememberScrollState())
       .padding(horizontal = 8.dp)

    ) {
        ConvertScreen(viewModel = convertViewModel, viewModel)

       Divider(thickness = 1.dp, modifier = Modifier.padding(32.dp))

        LiveExchangeScreen(viewModel = viewModel)
    }
}