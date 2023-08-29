package dev.sobhy.bmproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import dev.sobhy.bmproject.ui.screens.compare.CompareScreen
import dev.sobhy.bmproject.ui.screens.compare.CompareViewModel
import dev.sobhy.bmproject.ui.screens.convertandliveexcange.ConvertAndLiveScreen
import dev.sobhy.bmproject.ui.screens.convertandliveexcange.ConvertScreen.ConvertViewModel
import dev.sobhy.bmproject.ui.screens.convertandliveexcange.liveexchangefeature.LiveExchangeViewModel
import dev.sobhy.bmproject.ui.theme.BMProjectTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BMProjectTheme {
                val pagerState = rememberPagerState { 2 }
                val tabTitles = listOf(getString(R.string.convert), getString(R.string.compare))
                val coroutineScope = rememberCoroutineScope()

                Column(modifier = Modifier.fillMaxWidth()) {
                    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                        val (image, title, text, tab) = createRefs()

                        Image(
                            painter = painterResource(id = R.drawable.img),
                            contentDescription = "background image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .drawWithContent {
                                    drawContent()
                                    drawRect(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                Color.White.copy(alpha = 0.1f),
                                                Color.Black.copy(alpha = 0.5f)
                                            ),
                                            startY = size.height - (700),
                                            endY = size.height
                                        )
                                    )
                                }
                                .constrainAs(image) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                },
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            text = getString(R.string.concurrency),
                            fontSize = 25.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(start = 20.dp, top = 50.dp)
                                .constrainAs(title) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                })

                        Column(
                            modifier = Modifier
                                .padding(top = 32.dp)
                                .constrainAs(text) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(image.bottom)
                                },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = getString(R.string.currency_converter),
                                style = TextStyle(
                                    fontSize = 23.sp,
                                    fontWeight = FontWeight(600),
                                    color = Color.White,
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = getString(R.string.check_live_foreign_currency_exchange_rates),
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight(400),
                                    color = Color.White,
                                )
                            )
                        }

                        TabRow(
                            selectedTabIndex = pagerState.currentPage,
                            containerColor = Color(0xFFECECEC),
                            modifier = Modifier
                                .padding(horizontal = 50.dp)
                                .clip(RoundedCornerShape(50))
                                .constrainAs(tab) {
                                    top.linkTo(image.bottom)
                                    bottom.linkTo(image.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                },
                            indicator = {
                                Box { }
                            }
                        ) {
                            tabTitles.forEachIndexed { index, s ->
                                val selected = pagerState.currentPage == index
                                Tab(
                                    modifier = if (selected)
                                        Modifier
                                            .padding(6.dp)
                                            .clip(RoundedCornerShape(50))
                                            .background(Color.White)
                                    else Modifier,
                                    selected = selected,
                                    onClick = {
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(index)
                                        }
                                    },
                                    text = {
                                        Text(text = s, color = Color.Black, fontSize = 18.sp)
                                    })
                            }
                        }

                    }
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .wrapContentHeight()
                            .weight(1F)
                    ) { page ->
                        when (page) {
                            0 -> {
                                val viewModel: LiveExchangeViewModel by viewModels()
                                val convertViewModel =
                                    ViewModelProvider(this@MainActivity)[ConvertViewModel::class.java]
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    ConvertAndLiveScreen(convertViewModel, viewModel)

                                }
                            }

                            1 -> {
                                val compareViewModel =
                                    ViewModelProvider(this@MainActivity)[CompareViewModel::class.java]
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    CompareScreen(viewModel = compareViewModel)
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}