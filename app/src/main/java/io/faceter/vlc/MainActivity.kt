package io.faceter.vlc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import io.faceter.vlc.ui.theme.MultiVLCTheme

val colors = listOf(
    Color.Red,
    Color.Blue,
    Color.Yellow,
    Color.Gray,
    Color.Green,
    Color.Black,
    Color.Magenta,
    Color.LightGray,
    Color.DarkGray
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultiVLCTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VideoGrid(size = 3)

                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MultiVLCTheme {
        NonLazyGrid(columns = 3, itemCount = 9, fillMaxHeight = true) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors[it])
            )
        }
    }
}