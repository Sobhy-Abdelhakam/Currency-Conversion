package dev.sobhy.bmproject.ui.screens.compare


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun CompareScreen(viewModel: CompareViewModel) {

    viewModel.getCurrencies()
    val currencies = viewModel.observeCurrenciesLivedata().observeAsState().value
    var amountFrom by remember { mutableStateOf("") }
    var amountTo by remember { mutableStateOf("") }
    var amountToTwo by remember { mutableStateOf("") }
    val toCurrency = viewModel.observeFromLivedata().observeAsState().value?.code
    val fromCurrency = viewModel.observeToLivedata().observeAsState().value?.code
    val toCurrencyTwo = viewModel.observe_ToLivedata().observeAsState().value?.code


    val result = viewModel.observeCurrenciesCompareLivedata().observeAsState().value?.result
    val res = result?.let { (it * 10000.0).roundToInt() / 10000.0 } ?: ""
    amountTo = res.toString()

    val result1 = viewModel.currencyCompareLiveData1.observeAsState().value?.result
    val res1 = result1?.let { (it * 10000.0).roundToInt() / 10000.0 } ?: ""
    amountToTwo = res1.toString()



    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center

    ) {

        Column() {

            Text(
                text = "Amount",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF000000),
                ),
                modifier = Modifier
                    .padding(bottom = 15.dp)
            )
            OutlinedTextField(
                value = amountFrom,
                onValueChange = { amountFrom = it },
                keyboardOptions =
                KeyboardOptions(keyboardType = KeyboardType.Decimal),
                shape = RoundedCornerShape(size = 20.dp),
                singleLine = true,
                modifier = Modifier
                    .width(152.dp)
                    .height(49.dp)
                    .background(color = Color(0xFFF9F9F9))

            )
            Text(
                text = "Targeted currency",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF000000),
                ),
                modifier = Modifier
                    .padding(bottom = 15.dp, top = 15.dp)

            )
            DropdownMenuBox(currencies, viewModel)

            Spacer(modifier = Modifier.size(17.dp))
            OutlinedTextField(
                value = if (amountFrom.isNotBlank()) {
                    amountTo
                } else {
                    ""
                },
                onValueChange = { amountTo = it },
                shape = RoundedCornerShape(size = 20.dp),
                singleLine = true,
                modifier = Modifier
                    .width(152.dp)
                    .height(49.dp)
                    .background(color = Color(0xFFF9F9F9)),
                readOnly = true


            )


        }
        Spacer(modifier = Modifier.width(24.dp))
        Column() {

            Text(
                text = "From",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF000000),
                ),
                modifier = Modifier
                    .padding(bottom = 15.dp)

            )
            DropdownMenuBox1(
                currencies, viewModel,
                //onSelected = { target1 = it }
            )
            Text(
                text = "Targeted currency",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF000000),
                ),
                modifier = Modifier
                    .padding(bottom = 15.dp, top = 15.dp)

            )
            DropdownMenuBox2(
                currencies, viewModel,
                //onSelected = { target1 = it }
            )

            Spacer(modifier = Modifier.size(17.dp))
            OutlinedTextField(
                value = if (amountFrom.isNotBlank()) {
                    amountToTwo
                } else {
                    ""
                },
                onValueChange = { amountToTwo = it },
                shape = RoundedCornerShape(size = 20.dp),
                singleLine = true,
                modifier = Modifier
                    .width(152.dp)
                    .height(49.dp)
                    .background(color = Color(0xFFF9F9F9)),
                readOnly = true


            )
        }

    }
    Button(
        onClick = {
            if (toCurrency != null && fromCurrency != null && amountFrom.isNotEmpty()) {
                viewModel.compareCurrencies(fromCurrency, toCurrency, amountFrom.toDouble())
            }
            if (fromCurrency != null && toCurrencyTwo != null && amountFrom.isNotEmpty()) {
                viewModel.compareCurrencies1(fromCurrency, toCurrencyTwo, amountFrom.toDouble())
            }

            Log.d("devil", result.toString())


        },
        colors = ButtonDefaults.buttonColors(Color(0xFF363636)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(50.dp)
            .width(315.dp)
            .height(49.dp),
        shape = RoundedCornerShape(size = 20.dp),


        ) {
        Text(
            text = "Compare ",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight(700),
                color = Color.White,
            )


        )


    }


}



