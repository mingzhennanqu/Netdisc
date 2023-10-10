package com.lulu.favorite.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.MimeTypes
import androidx.media3.exoplayer.analytics.AnalyticsListener
import com.lulu.favorite.ui.utils.Constants
import io.sanghun.compose.video.RepeatMode
import io.sanghun.compose.video.VideoPlayer
import io.sanghun.compose.video.controller.VideoPlayerControllerConfig
import io.sanghun.compose.video.uri.VideoPlayerMediaItem

@Composable
fun videoP(
    url: MutableState<String>
) {
    VideoPlayer(
        //视频媒体项列表，用于指定要播放的视频资源
        mediaItems = listOf(
            //从程序内部预置的资源文件加载视频,通常位于res/raw目录下
//            VideoPlayerMediaItem.RawResourceMediaItem(
//                resourceId = R.raw.test,
//            ),
            //从应用程序的资产文件（asset file）中加载视频,通常位于assets目录下
//            VideoPlayerMediaItem.AssetFileMediaItem(
//                assetPath = "assets/test.mp4"
//            ),
            //从存储器中加载视频。可以指定存储器上的文件路径或URI来加载视频文件。例如，可以指定SD卡上的视频文件路径来加载外部存储器中的视频
//            VideoPlayerMediaItem.StorageMediaItem(
//                storageUri = Uri.parse("")
//            ),
            //从网络中加载视频。可以指定视频的URL地址来加载网络视频
            VideoPlayerMediaItem.NetworkMediaItem(
//                url = "http://192.168.2.157:8080/mysql/%E5%BD%B1%E8%A7%86/%E9%87%9C%E5%B1%B1%E8%A1%8C/%E9%87%9C%E5%B1%B1%E8%A1%8C.mp4",
                url = Constants.UrlTom + url.value,
                //标题
                mediaMetadata = MediaMetadata.Builder().setTitle("Widevine DASH cbcs: Tears").build(),
//                mimeType = MimeTypes.APPLICATION_MPD,
                //编码格式
                mimeType = MimeTypes.VIDEO_H264,
                drmConfiguration = MediaItem.DrmConfiguration.Builder(C.WIDEVINE_UUID)
                    .setLicenseUri("https://proxy.uat.widevine.com/proxy?provider=widevine_test")
                    .build(),
            )
        ),
        //控制是否自动处理生命周期。当设置为true时，组件会自动根据界面的生命周期状态来管理视频播放的开始、暂停、停止等操作。
        handleLifecycle = true,

        //指定视频是否自动开始播放
        autoPlay = false,

        //控制是否使用内置的播放器控制器。当设置为true时，会显示默认的播放器控制栏（包括播放/暂停按钮、进度条等），可以交互地控制视频播放。
        usePlayerController = true,

        //控制是否启用画中画模式（Picture-in-Picture）。当设置为true时，用户可以将视频窗口缩小为一个浮动窗口，并在其他应用程序上方进行悬浮播放。
        enablePip = true,

        //控制是否处理音频焦点。当设置为true时，播放器会自动处理与音频焦点相关的事件，例如暂停播放当其他应用程序请求音频焦点时。
        handleAudioFocus = true,

        //播放器控制栏的配置参数，用于设置控制栏的外观和功能
        controllerConfig = VideoPlayerControllerConfig(
            //控制是否显示播放速度和音调的选项。当设置为true时，会在播放器控制栏上显示播放速度和音调的选择按钮，用户可以通过点击按钮来调整播放速度和音调。
            showSpeedAndPitchOverlay = false,

            //控制是否显示字幕按钮。当设置为true时，会在播放器控制栏上显示一个字幕按钮，用户可以通过点击按钮来切换显示或隐藏视频的字幕。
            showSubtitleButton = false,

            //控制是否显示当前时间和总时间。当设置为true时，会在播放器控制栏上显示当前播放时间和视频总时间。
            showCurrentTimeAndTotalTime = true,

            //控制是否显示缓冲进度。当设置为true时，会在播放器控制栏上显示视频的缓冲进度条，以显示视频加载的进度。
            showBufferingProgress = false,

            //控制是否显示快进按钮。当设置为true时，会在播放器控制栏上显示一个快进按钮，用户可以通过点击按钮来进行快进操作。
            showForwardIncrementButton = true,

            //控制是否显示快退按钮。当设置为true时，会在播放器控制栏上显示一个快退按钮，用户可以通过点击按钮来进行快退操作。
            showBackwardIncrementButton = true,

            //控制是否显示全屏按钮。当设置为true时，会在播放器控制栏上显示一个全屏按钮，用户可以通过点击按钮来切换视频的全屏显示。
            showFullScreenButton = true,

            //控制是否显示后退按钮。当设置为true时，会在播放器控制栏上显示一个后退按钮，用户可以通过点击按钮来进行后退操作。
            showBackTrackButton = true,

            //控制是否显示下一个视频按钮。当设置为true时，会在播放器控制栏上显示一个下一个视频按钮，用户可以通过点击按钮来切换到下一个视频。
            showNextTrackButton = true,

            //控制是否显示循环模式按钮。当设置为true时，会在播放器控制栏上显示一个循环模式按钮，用户可以通过点击按钮来切换视频的循环模式（例如不循环、全部循环、单个循环）。
            showRepeatModeButton = true,

            //控制播放器控制栏的显示时间（单位为毫秒）。例如，设置为5_000表示播放器控制栏会在用户操作后的5秒钟内保持可见，之后会自动隐藏。
            controllerShowTimeMilliSeconds = 5_000,

            //控制播放器控制栏是否自动显示。当设置为true时，在播放器开始播放时或用户进行操作时，播放器控制栏会自动显示。
            controllerAutoShow = true,
        ),
        //指定视频的音量大小，范围从0.0f到1.0f
        volume = 0.5f,  // volume 0.0f to 1.0f

        //指定视频的循环模式，可选值为None（不循环）、All（全部循环）或One（单个循环）。
        repeatMode = RepeatMode.NONE,       // or RepeatMode.ALL, RepeatMode.ONE

        //当视频当前播放时间改变时的回调函数。
        onCurrentTimeChanged = { // long type, current player time (millisec)
            Log.e("CurrentTime", it.toString())
        },

        //ExoPlayer实例，用于对播放器进行更高级的自定义配置
        playerInstance = { // ExoPlayer instance (Experimental)
            addAnalyticsListener(
                object : AnalyticsListener {
                    // player logger
                }
            )
        },
        modifier = Modifier
            .fillMaxSize()
//            .align(Alignment.Center),
    )
}