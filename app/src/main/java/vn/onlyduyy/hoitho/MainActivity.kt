package vn.onlyduyy.hoitho

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import vn.onlyduyy.hoitho.ui.theme.HoithoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HoithoTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MyApp()
                }
            }
        }
    }
}


@Composable
fun Dashboard(onStartClicked: () -> Unit,modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text= "Your better self!", style = TextStyle(fontSize = 20.sp, fontFamily = FontFamily.Serif))
        Button(onClick = onStartClicked) {
            Text(text = "Breath!")
        }

    }
}

@Composable
fun LearnToBreath(modifier: Modifier = Modifier) {
    val mContext = LocalContext.current
    var timeLeft by remember { mutableStateOf(10) }
    var isStart by remember {mutableStateOf(false)}
    val inhale = MediaPlayer.create(mContext, R.raw.inhale_audio)
    val exhale = MediaPlayer.create(mContext, R.raw.exhale_audio)

    if (isStart) {
        LaunchedEffect(key1 = timeLeft) {
            if (timeLeft == 10) {
                inhale.start()
            }
            while (timeLeft >= 0) {
                if (timeLeft == 5) {
                    exhale.start()
                } else if (timeLeft == 0) {
                    timeLeft = 12
                }
                delay(1000L)
                timeLeft--
            }

        }
    }

    Column(
        modifier = modifier.padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "$timeLeft", style= TextStyle(fontSize = 48.sp, fontFamily = FontFamily.SansSerif) )
        Button(onClick = {
            isStart = true
        }) {
            Text(text = "Start")
            if (!isStart) {
                Text(text = "Pause")
            }
        }
    }
}


@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var showingDashboard by remember { mutableStateOf(true) }
    Surface(modifier.fillMaxSize()) {
        if (showingDashboard) {
            Dashboard(onStartClicked = { showingDashboard = false })
        } else {
            LearnToBreath()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HoithoTheme {
        MyApp()
    }
}