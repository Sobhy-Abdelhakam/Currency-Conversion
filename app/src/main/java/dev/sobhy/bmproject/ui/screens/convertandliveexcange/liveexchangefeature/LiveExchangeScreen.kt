package dev.sobhy.bmproject.ui.screens.convertandliveexcange.liveexchangefeature

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import dev.sobhy.bmproject.ui.screens.composable.Loading
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun LiveExchangeScreen(viewModel: LiveExchangeViewModel) {
    val context = LocalContext.current
    var showDialog by remember {
        mutableStateOf(false)
    }
    viewModel.getSavedCurrencies()
    val state = viewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        if (showDialog) {
            MyFavoritesDialog(true, viewModel, onDismiss = { showDialog = false })
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "live exchange rates", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            OutlinedButton(onClick = {
                showDialog = true
            }) {
                Icon(imageVector = Icons.Filled.AddCircleOutline, contentDescription = "Add icon")
                Text(text = "Add to favorite")
            }
        }
        Text(text = "My Portfolio", fontSize = 18.sp, modifier = Modifier.padding(top = 12.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()) {
            if (state.value.savedCurrencies.isEmpty()) {
                Text(text = "There are no saved Currencies" , modifier = Modifier.padding(8.dp))
            }
            Loading(isLoading = state.value.isLoading, modifier = Modifier.align(Alignment.Center))

            Column(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()) {
                val list = state.value.savedCurrencies
                repeat(list.size) { index ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 14.dp)
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(context).data(list[index].flagUrl)
                                .decoderFactory(SvgDecoder.Factory()).build(),
                            contentDescription = list[index].desc,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(text = list[index].code, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = list[index].desc, color = Color(0xFFB8B8B8))
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        if (list[index].amount != 0.0) {
                            val amountAfterRound =
                                BigDecimal(list[index].amount).setScale(4, RoundingMode.HALF_EVEN)
                                    .toDouble()
                            Text(text = amountAfterRound.toString(), fontWeight = FontWeight.Bold)
                        }
                    }
                    Divider(modifier = Modifier.height(0.5.dp))
                }
            }
        }

    }

}