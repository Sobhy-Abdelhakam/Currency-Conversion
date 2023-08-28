package dev.sobhy.bmproject.ui.screens.convertandliveexcange.ConvertScreen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import dev.sobhy.bmproject.ui.screens.convertandliveexcange.liveexchangefeature.LiveExchangeViewModel

@Composable
fun ConvertScreen(viewModel: ConvertViewModel, viewModel1: LiveExchangeViewModel) {

    viewModel.getCurrencies()
    val currencies = viewModel.observeCurrenciesLivedata().observeAsState().value
    var amountFrom by remember { mutableStateOf("") }
    var amountTo by remember { mutableStateOf("") }
    val toCurrency = viewModel.observeFromLivedata().observeAsState().value?.code
    val fromCurrency = viewModel.observeToLivedata().observeAsState().value?.code


    val result = viewModel.observeCurrenciesConvertLivedata().observeAsState().value?.result
    amountTo = result.toString()


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top= 32.dp)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.height(20.dp))
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
                    text = "To",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF000000),
                    ),
                    modifier = Modifier
                        .padding(bottom = 15.dp, top = 15.dp)

                )
                DropdownMenuBox(currencies, viewModel)


            }
            Spacer(modifier = Modifier.width(28.dp))
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
                DropdownMenuBox1(currencies, viewModel)
                Text(
                    text = "Amount",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF000000),
                    ),
                    modifier = Modifier
                        .padding(bottom = 15.dp, top = 15.dp)

                )

                OutlinedTextField(
                    value = if(amountFrom.isNotBlank()){
                        amountTo
                    } else{
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

        }
        Button(
            onClick = {
                if (toCurrency != null && fromCurrency != null && amountFrom.isNotEmpty()) {
                    viewModel.convertCurrencies(fromCurrency, toCurrency, amountFrom.toDouble())
                    viewModel1.getCompareResponseAndSaveInDatabaseWithAmountValue(
                        amountFrom.toDouble(),
                        fromCurrency
                    )
                }
            },
            colors = ButtonDefaults.buttonColors(Color(0xFF363636)),
            modifier = Modifier
                .padding(top = 30.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(49.dp),
            shape = RoundedCornerShape(size = 25.dp),


            ) {
            Text(
                text = "Convert ",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFFFFFFFF),
                )


            )
        }
    }


}





