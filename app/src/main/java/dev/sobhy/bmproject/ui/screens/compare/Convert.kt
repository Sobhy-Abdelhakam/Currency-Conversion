package dev.sobhy.bmproject.ui.screens.compare

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ConvertScreen(){
    val viewModel = CompareViewModel()
    viewModel.compareCurrencies("AED","GBP",126.0)
    val x = viewModel.observeCurrenciesCompareLivedata().observeAsState().value?.result
    Log.d("devil",x.toString())
Text(text = x.toString(),
    modifier = Modifier.padding(top = 400.dp)

)
}