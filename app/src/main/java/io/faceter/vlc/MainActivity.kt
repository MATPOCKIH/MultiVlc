package io.faceter.vlc

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import io.faceter.vlc.ui.theme.MultiVLCTheme
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import org.videolan.libvlc.util.VLCVideoLayout

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
                    VideoGrid()
                    /*NonlazyGrid(columns = 3, itemCount = 9, modifier = Modifier
                            .padding(start = 7.5.dp, end = 7.5.dp)) {
                        /*VideoPlayer(
                            modifier = Modifier.background(colors[it]))*/
                        Box(modifier = Modifier.fillMaxHeight().background(colors[it]))

                    }*/
                }
            }
        }
    }
}

@Composable
fun VideoGrid() {
    // This is the official way to access current context from Composable functions
    val context = LocalContext.current

    val mLibVLC = remember {
        LibVLC(context, ArrayList<String>().apply {
            add("--rtsp-tcp")
            add("-vvv")
        })
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Blue)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            VideoPlayer(
                mLibVLC,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(colors[0])
            )
            VideoPlayer(
                mLibVLC,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(colors[1])
            )
            VideoPlayer(
                mLibVLC,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(colors[2])
            )
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            VideoPlayer(
                mLibVLC,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(colors[3])
            )
            VideoPlayer(
                mLibVLC,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(colors[4])
            )
            VideoPlayer(
                mLibVLC,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(colors[5])
            )
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            VideoPlayer(
                mLibVLC,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(colors[6])
            )
            VideoPlayer(
                mLibVLC,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(colors[7])
            )
            VideoPlayer(
                mLibVLC,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(colors[8])
            )
        }

    }
}

@Composable
fun VideoPlayer(mLibVLC: LibVLC?, modifier: Modifier = Modifier) {

    val mMediaPlayer = remember {
        val url = "rtsp://facetercamstorage-01.dev.avalab.io:8554/gend/sd"

        MediaPlayer(mLibVLC).apply {
            val uri = Uri.parse(url)

            val media = Media(mLibVLC, uri).apply {
                setHWDecoderEnabled(true, false)
                addOption(":network-caching=1000")
                addOption(":rtsp−caching=100")
                addOption(":clock-jitter=0")
                addOption(":clock-synchro=0")
            }
            volume = 0
            this.media = media
            media.release()
        }
    }

    Box(
        modifier = modifier
    ) {
        // Gateway to traditional Android Views
        AndroidView(
            factory = { context ->
                val layout = VLCVideoLayout(context)
                mMediaPlayer.detachViews()
                mMediaPlayer.attachViews(layout, null, false, true)
                mMediaPlayer.play()
                layout
            },
            modifier = modifier
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MultiVLCTheme {
        VideoGrid()
    }
}


@Composable
fun NonlazyGrid(
    columns: Int,
    itemCount: Int,
    modifier: Modifier = Modifier,
    content: @Composable() (Int) -> Unit
) {
    Column(modifier = modifier) {
        var rows = (itemCount / columns)
        if (itemCount.mod(columns) > 0) {
            rows += 1
        }

        for (rowId in 0 until rows) {
            val firstIndex = rowId * columns

            Row {
                for (columnId in 0 until columns) {
                    val index = firstIndex + columnId
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Magenta)
                            .weight(1f)
                    ) {
                        if (index < itemCount) {
                            content(index)
                        }
                    }
                }
            }
        }
    }
}