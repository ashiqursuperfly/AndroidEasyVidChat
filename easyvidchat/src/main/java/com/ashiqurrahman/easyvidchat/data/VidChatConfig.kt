package com.ashiqurrahman.easyvidchat.data

import com.ashiqurrahman.easyvidchat.R

/* Created by ashiq.buet16 **/

object VidChatConfig {

    var videoCallEnabled = true
    var screenCaptureEnabled = false

    var useCamera2API = true

    var videoCodec = VideoCodec.VP8
    var audioCodec = AudioCodec.OPUS

    var hwCodec = true
    var captureToTexture = true

    var flexFecEnabled = false

    // Check Disable Audio Processing flag.
    var audioProcessingDisabled = false
    var aecDump = false

    var openSLESEnabled = false
    var builtInAECDisabled = false
    var builtInAGCDisabled = false

    var disableBuiltInNS = false
    var disableWebRTCAGCAndHPF = false

    var videoResolution = VideoResolution.DEFAULT
    var cameraFPS = FPSValues.DEFAULT

    var captureQualitySliderEnable = false
    var videoStartBitrate = 1700
    var audioStartBitrate = 32

    // stats
    var isDebug = false
    var displayHUD = false
    var tracingEnabled = false
    var rtcEventLogEnabled = false
    var saveInputAudioToFile = false

    var dataChannelEnabled = true
    var isOrdered = true
    var isNegotiated = false

    const val speakerPhone = "auto"

    const val maxReTransmitTimeMillis = -1
    const val maxReTransmits = -1

    const val dataID =  -1
    const val protocol = ""


    object AlertDialogUI {
        var alertDialogBgColorRes: Int = R.color.color1
        var iconDrawableRes: Int = R.drawable.ic_video_call
        var btnTextColorRes: Int = R.color.color1
        var btnBgColorRes: Int = R.color.color2
        var titleTextColorHexSixDigitString = "#000000"
        var textMsgColorHexSixDigitString ="#000000"
    }

    object VidChatIcons {
        var micIcon: Int = R.drawable.ic_mic
        var disconnectIcon: Int = R.drawable.ic_disconnect
        var fullScreenIcon: Int = R.drawable.ic_fullscreen
        var fullScreenExitIcon: Int = R.drawable.ic_fullscreen_exit
        var switchCameraIcon: Int = R.drawable.ic_switch_camera
    }

}

enum class VideoCodec(var codecName: String) {
    VP8("VP8"), VP9("VP9"), H264_BASELINE("H264 Baseline"), H264_HIGH("H264 High")
}

enum class AudioCodec {
    OPUS, ISAC
}

enum class VideoResolution(var resolution: String) {
    DEFAULT("Default"),
    _4K("3840 x 2160"),
    FHD("1920 x 1080"),
    HD("1280 x 720"),
    VGA("640 x 480"),
    QVGA("320 x 240")
}

enum class FPSValues(var fps: String) {
    DEFAULT("Default"),
    _30("30 fps"),
    _15("15 fps")
}





