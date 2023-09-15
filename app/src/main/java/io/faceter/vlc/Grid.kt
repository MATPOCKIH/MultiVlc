package io.faceter.vlc

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import org.videolan.libvlc.LibVLC

@Composable
fun VideoGrid(size: Int = 3, hdQuality: Boolean) {

    // This is the official way to access current context from Composable functions
    val context = LocalContext.current

    val mLibVLC = remember {
        LibVLC(context, ArrayList<String>().apply {
            add("--rtsp-tcp")
            add("-vvv")
        })
    }

    val configuration = LocalConfiguration.current

    val fillMaxHeight =
        configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    NonLazyGrid(
        columns = size,
        itemCount = size * size,
        fillMaxHeight = fillMaxHeight,
        modifier = Modifier
            .padding(0.dp)
    ) {

        Box(
            modifier = Modifier.border(width = 0.5.dp, color = Color.White)
        ) {
            VideoPlayer(mLibVLC, hdQuality)
        }
    }

    DisposableEffect(key1 = Unit, effect = {
        onDispose {
            Log.d("Anton", "onDispose Grid")
            mLibVLC.release()
        }
    })
}
