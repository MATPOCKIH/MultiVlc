package io.faceter.vlc

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import org.videolan.libvlc.util.VLCVideoLayout


@Composable
fun VideoPlayer(mLibVLC: LibVLC?, modifier: Modifier = Modifier) {

    val mMediaPlayer = remember {
        val url = "rtsp://facetercamstorage-01.dev.avalab.io:8554/gend/sd"

        MediaPlayer(mLibVLC).apply {
            val uri = Uri.parse(url)

            val media = Media(mLibVLC, uri).apply {
                setHWDecoderEnabled(true, false)
                addOption(":network-caching=300")
                addOption(":rtsp−caching=100")
                addOption(":clock-jitter=0")
                addOption(":clock-synchro=0")
            }
            volume = 0
            this.media = media
            media.release()
        }
    }
    // Gateway to traditional Android Views
    AndroidView(
        factory = { context ->
            val layout = VLCVideoLayout(context)
            mMediaPlayer.detachViews()
            mMediaPlayer.attachViews(layout, null, false, true)
            mMediaPlayer.videoScale = MediaPlayer.ScaleType.SURFACE_FILL
            mMediaPlayer.setUseOrientationFromBounds(true)
            mMediaPlayer.play()
            layout
        }
    )
}