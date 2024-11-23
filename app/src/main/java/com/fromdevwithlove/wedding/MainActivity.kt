package com.fromdevwithlove.wedding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fromdevwithlove.wedding.ui.CounterUiState
import com.fromdevwithlove.wedding.ui.CounterViewModel
import com.fromdevwithlove.wedding.ui.theme.WeddingCounterTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.joda.time.Period

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Handle the splash screen transition.
        installSplashScreen()

        setContent {
            WeddingCounterTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent
                )
                systemUiController.setStatusBarColor(Color(0x64A0E7E5))
                systemUiController.setNavigationBarColor(Color(0x64A0E7E5))
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.eucaliptus2),
                        contentDescription = null,
                        alignment = Alignment.TopCenter,
                        contentScale = ContentScale.Crop,
                        alpha = 0.8F
                    )

                    val counterViewModel: CounterViewModel = viewModel()
                    CounterHolder(counterViewModel.counterUiState)

                    BoxWithConstraints(
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        if (maxHeight > 500.dp) {
                            Image(
                                painter = painterResource(id = R.drawable.ms_mr_test),
                                contentDescription = null,
                                alignment = Alignment.BottomCenter,
                                contentScale = ContentScale.FillWidth,
                                alpha = 0.7F,
                                modifier = Modifier
                                    .padding(start = 40.dp, end = 40.dp, bottom = 40.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

val color = Color.Black.value

@Composable
fun CounterHolder(counterUiState: CounterUiState) {
    when (counterUiState) {
        is CounterUiState.AfterWedding -> Counter(counterUiState.duration, "Od Ślubu minęło:")
        is CounterUiState.BeforeWedding -> Counter(counterUiState.duration, "Do Ślubu pozostało:")
        is CounterUiState.Calculating -> CalculatingState()
    }
}

@Composable
fun Counter(duration: Period?, counterLabel: String) {
    duration?.let { period ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = Color(0x64A0E7E5))
                    .padding(10.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = counterLabel,
                        fontFamily = FontFamily.Serif,
                        color = Color(color),
//                        fontSize = 25.sp,
                        fontSize = 45.sp,
                        modifier = Modifier
                    )
                    CounterDetail(
                        text = pluralStringResource(
                            id = R.plurals.months,
                            count = period.months,
                            period.months
                        )
                    )

                    CounterDetail(
                        text = pluralStringResource(
                            id = R.plurals.days,
                            count = period.days,
                            period.days
                        )
                    )
                    CounterDetail(
                        text = pluralStringResource(
                            id = R.plurals.hours,
                            count = period.hours,
                            period.hours
                        )
                    )
                    CounterDetail(
                        text = pluralStringResource(
                            id = R.plurals.minutes,
                            count = period.minutes,
                            period.minutes
                        )
                    )
                    CounterDetail(
                        text = pluralStringResource(
                            id = R.plurals.seconds,
                            count = period.seconds,
                            period.seconds
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun CounterDetail(text: String) {
    Text(
        text = text,
        fontFamily = FontFamily.Serif,
        color = Color(color),
//        fontSize = 20.sp,
        fontSize = 35.sp,
        modifier = Modifier
    )
}

@Composable
fun CalculatingState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(12.dp))
                .background(color = Color(0x64A0E7E5))
                .padding(10.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Liczę, liczę...",
                    fontFamily = FontFamily.Serif,
                    color = Color(color),
//                    fontSize = 25.sp,
                    fontSize = 45.sp,
                    modifier = Modifier
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeddingCounterTheme {
//        Counter()
    }
}