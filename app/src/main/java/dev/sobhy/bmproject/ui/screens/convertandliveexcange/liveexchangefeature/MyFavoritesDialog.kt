package dev.sobhy.bmproject.ui.screens.convertandliveexcange.liveexchangefeature

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import dev.sobhy.bmproject.data.model.Currency
import dev.sobhy.bmproject.ui.screens.composable.Loading

@Composable
fun MyFavoritesDialog(show: Boolean, viewModel: LiveExchangeViewModel, onDismiss: () -> Unit) {
    if (!show) return
    viewModel.getCurrencies()

    Dialog(
        onDismissRequest = onDismiss::invoke,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Close Icon",
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(32.dp)
                    .clickable { onDismiss() }
                    .padding(bottom = 32.dp)
            )
            Card(
                modifier = Modifier.height(550.dp),
                colors = CardDefaults.cardColors(Color(0xFFF8F8F8)),
                shape = RoundedCornerShape(20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp)
                ) {
                    val state = viewModel.state.collectAsState()
                    Loading(isLoading = state.value.isLoading,)
                    Column(
                        modifier = Modifier
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "My Favorites",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        LazyColumn {
                            items(state.value.currencyResponse) { currency ->
                                var radioButtonState by remember {
                                    mutableStateOf(currency.isSaved)
                                }
                                radioButtonState = currency.isSaved
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(4.dp)
                                        .clickable {
                                            radioButtonState = !radioButtonState
                                            if (radioButtonState) {
                                                viewModel.saveCurrency(currency)
                                            } else {
                                                viewModel.deleteCurrency(currency)
                                            }
                                        },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    FavoriteItem(currency = currency)
                                    Spacer(modifier = Modifier.weight(1f))
                                    val icon = if (radioButtonState) {
                                        Icons.Filled.CheckCircle
                                    } else {
                                        Icons.Outlined.Circle
                                    }
                                    Icon(imageVector = icon, contentDescription = "")
                                }
                                Divider(modifier = Modifier.height(2.dp))
                            }
                        }
                    }
                }
            }
        }


    }
}

@Composable
fun FavoriteItem(currency: Currency) {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context).data(currency.flagUrl)
                .decoderFactory(SvgDecoder.Factory()).build(),
            contentDescription = currency.desc,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = currency.code, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = currency.desc, color = Color(0xFFB8B8B8))
        }
    }
}