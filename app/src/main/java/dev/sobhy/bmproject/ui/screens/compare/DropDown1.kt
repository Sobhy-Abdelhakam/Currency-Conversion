package dev.sobhy.bmproject.ui.screens.compare


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import dev.sobhy.bmproject.data.model.Currency


@OptIn(ExperimentalMaterial3Api::class, )
@Composable
fun DropdownMenuBox1(currency: List<Currency>?, viewModel: CompareViewModel) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(Currency(
        "CAD",
        "https://www.xe.com/static-images/cad.static.0bfb6a6d0adf4f3b1c28c0c90248b18b.svg",
        "",
        false,
        0.0
    ) ) }

    Box {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = " ${selectedText?.code}"?:"Select",
                onValueChange = {},
                readOnly = true,
                leadingIcon = {
                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .decoderFactory(SvgDecoder.Factory())
                            .data(selectedText?.flagUrl)
                            .build()
                    )
                    Image(
                        painter = painter,
                        modifier = Modifier
                            .width(28.dp)
                            .height(20.dp)
                        ,
                        contentDescription = null
                    )

                },
                shape = RoundedCornerShape(size = 20.dp),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .width(152.dp)
                    .height(49.dp)
                    .background(color = Color(0xFFF9F9F9))

            )



            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                currency?.forEach() { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.code) },
                        onClick = {
                            selectedText = item
                            viewModel.to.value = item
                            expanded = false
                            Toast.makeText(context, item.code, Toast.LENGTH_SHORT).show()
                        },
                        leadingIcon = {
                            val painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .decoderFactory(SvgDecoder.Factory())
                                    .data(item.flagUrl)
                                    .size(Size.ORIGINAL) // Set the target size to load the image at.
                                    .build()
                            )
                            Image(
                                painter = painter,
                                modifier = Modifier
                                    .width(28.dp)
                                    .height(20.dp)
                                ,
                                contentDescription = null
                            )
                        }


                    )
                }
            }
        }
    }
}
