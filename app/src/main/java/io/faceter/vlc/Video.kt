package io.faceter.vlc

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.viewinterop.AndroidView
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import org.videolan.libvlc.util.VLCVideoLayout

@Composable
fun VideoPlayer(mLibVLC: LibVLC?, hdQuality: Boolean) {

    val url = "rtsp://facetercamstorage-01.dev.avalab.io:8554/gend/" + if(hdQuality) "hd" else "sd"

    val mMediaPlayer = remember {
        MediaPlayer(mLibVLC).apply {
            val uri = Uri.parse(url)

            val media = Media(mLibVLC, uri).apply {
                setHWDecoderEnabled(true, false)
                addOption(":network-caching=1300")
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

    DisposableEffect(key1 = Unit, effect = {
        onDispose {
            Log.d("Anton", "onDispose Video")
            mMediaPlayer.stop()
            mMediaPlayer.release()
        }
    })
}