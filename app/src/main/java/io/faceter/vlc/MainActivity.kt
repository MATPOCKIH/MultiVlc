package io.faceter.vlc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.faceter.vlc.ui.theme.MultiVLCTheme
import androidx.activity.addCallback
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.runtime.State
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val columns = mutableStateOf<Int?>(null)
        val hdQuality = mutableStateOf(false)

        onBackPressedDispatcher.addCallback(this) {
            columns.value = null
        }

        setContent {
            MultiVLCTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if(columns.value == null) {
                        HomeScreen(
                            checked = hdQuality,
                            onColumnsSelected = {
                                columns.value = it
                            },
                            onSwitchChanged = {
                                hdQuality.value = it
                            }
                        )


                    } else {
                        val size = columns.value ?: 3
                        VideoGrid(size, hdQuality.value)
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    checked: State<Boolean>,
    onColumnsSelected: (Int) -> Unit,
    onSwitchChanged: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { onColumnsSelected(2) }) {
            Text(text = "2x2")
        }
        Button(onClick = { onColumnsSelected(3) }) {
            Text(text = "3x3")
        }
        Button(onClick = { onColumnsSelected(4) }) {
            Text(text = "4x4")
        }

        Row (verticalAlignment = Alignment.CenterVertically){
            Text(text = "SD")
            Spacer(modifier = Modifier.width(5.dp))
            Switch(checked = checked.value, onCheckedChange = {
                onSwitchChanged(it)
            })
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "HD")
        }

    }
}