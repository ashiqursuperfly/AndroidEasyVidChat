package com.ashiqurrahman.easyvidchat

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import com.ashiqurrahman.easyvidchat.data.VidChatConfig
import com.ashiqurrahman.easyvidchat.data.VidChatConsts
import com.ashiqurrahman.easyvidchat.rtc_util.UrlValidator.validateUrl
import com.ashiqurrahman.easyvidchat.ui.CallActivity

/* Created by ashiq.buet16 **/

object VidChat {

    private val permissions = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA,
        Manifest.permission.CHANGE_NETWORK_STATE,
        Manifest.permission.MODIFY_AUDIO_SETTINGS,
        Manifest.permission.INTERNET,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_NETWORK_STATE
    )

    const val TAG = "VidChatInit"

    fun getCallingIntent(
        activity: Activity,
        roomID: String
    ): Intent {

        val roomUrl = VidChatConsts.ROOM_BASE_URL

        // Video call enabled flag.
        val videoCallEnabled = VidChatConfig.videoCallEnabled

        // Use screencapture option.
        val useScreencapture = VidChatConfig.screenCaptureEnabled

        // Use Camera2 option.
        val useCamera2 = VidChatConfig.useCamera2API

        // Get default codecs.
        val videoCodec = VidChatConfig.videoCodec.codecName
        val audioCodec = VidChatConfig.audioCodec.name

        // Check HW codec flag.
        val hwCodec: Boolean = VidChatConfig.hwCodec

        // Check Capture to texture.
        val captureToTexture = VidChatConfig.captureToTexture

        // Check FlexFEC.
        val flexfecEnabled = VidChatConfig.flexFecEnabled

        // Check Disable Audio Processing flag.
        val noAudioProcessing = VidChatConfig.audioProcessingDisabled

        val aecDump = VidChatConfig.aecDump

        val saveInputAudioToFile = VidChatConfig.saveInputAudioToFile

        // Check OpenSL ES enabled flag.
        val useOpenSLES = VidChatConfig.openSLESEnabled

        // Check Disable built-in AEC flag.
        val disableBuiltInAEC = VidChatConfig.builtInAECDisabled

        // Check Disable built-in AGC flag.
        val disableBuiltInAGC = VidChatConfig.builtInAGCDisabled

        // Check Disable built-in NS flag.
        val disableBuiltInNS = VidChatConfig.disableBuiltInNS

        // Check Disable gain control
        val disableWebRtcAGCAndHPF = VidChatConfig.disableWebRTCAGCAndHPF

        // Get video resolution from settings.
        var videoWidth = 0
        var videoHeight = 0

        val resolution: String = VidChatConfig.videoResolution.resolution

        val dimensions = resolution.split("[ x]+").toTypedArray()
        if (dimensions.size == 2) {
            try {
                videoWidth = dimensions[0].toInt()
                videoHeight = dimensions[1].toInt()
            } catch (e: NumberFormatException) {
                Log.e(TAG, "Wrong video resolution setting:$resolution")
            }
        }

        // Get camera fps from settings.
        var cameraFps = 0
        val fps: String = VidChatConfig.cameraFPS.fps

        val fpsValues = fps.split("[ x]+").toTypedArray()
        if (fpsValues.size == 2) {
            try {
                cameraFps = fpsValues[0].toInt()
            } catch (e: java.lang.NumberFormatException) {
                cameraFps = 0
                Log.e(TAG, "Wrong camera fps setting: $fps")
            }
        }


        // Check capture quality slider flag.
        val captureQualitySlider = VidChatConfig.captureQualitySliderEnable

        // Get video and audio start bitrate.
        val videoStartBitrate = VidChatConfig.videoStartBitrate
        val audioStartBitrate = VidChatConfig.audioStartBitrate

        // Check statistics display option.
        val displayHud = VidChatConfig.displayHUD
        val tracing = VidChatConfig.tracingEnabled

        // Check Enable RtcEventLog.
        val rtcEventLogEnabled = VidChatConfig.rtcEventLogEnabled

        // Get datachannel options
        val dataChannelEnabled = VidChatConfig.dataChannelEnabled
        val ordered = VidChatConfig.isOrdered
        val negotiated = VidChatConfig.isNegotiated
        val maxReTrMs = VidChatConfig.maxReTransmitTimeMillis
        val maxReTr = VidChatConfig.maxReTransmits
        val id: Int = VidChatConfig.dataID
        val protocol = VidChatConfig.protocol

        // Start AppRTCMobile activity.
        Log.d(TAG, "Connecting to room $roomID at URL $roomUrl")
        val intent = Intent(activity, CallActivity::class.java)
        if (validateUrl(roomUrl, activity)) {
            val uri = Uri.parse(roomUrl)

            intent.data = uri
            intent.putExtra(VidChatConsts.EXTRA_ROOMID, roomID)
            intent.putExtra(VidChatConsts.EXTRA_VIDEO_CALL, videoCallEnabled)
            intent.putExtra(VidChatConsts.EXTRA_SCREENCAPTURE, useScreencapture)
            intent.putExtra(VidChatConsts.EXTRA_CAMERA2, useCamera2)
            intent.putExtra(VidChatConsts.EXTRA_VIDEO_WIDTH, videoWidth)
            intent.putExtra(VidChatConsts.EXTRA_VIDEO_HEIGHT, videoHeight)
            intent.putExtra(VidChatConsts.EXTRA_VIDEO_FPS, cameraFps)
            intent.putExtra(
                VidChatConsts.EXTRA_VIDEO_CAPTUREQUALITYSLIDER_ENABLED,
                captureQualitySlider
            )
            intent.putExtra(VidChatConsts.EXTRA_VIDEO_BITRATE, videoStartBitrate)
            intent.putExtra(VidChatConsts.EXTRA_VIDEOCODEC, videoCodec)
            intent.putExtra(VidChatConsts.EXTRA_HWCODEC_ENABLED, hwCodec)
            intent.putExtra(VidChatConsts.EXTRA_CAPTURETOTEXTURE_ENABLED, captureToTexture)
            intent.putExtra(VidChatConsts.EXTRA_FLEXFEC_ENABLED, flexfecEnabled)
            intent.putExtra(VidChatConsts.EXTRA_NOAUDIOPROCESSING_ENABLED, noAudioProcessing)
            intent.putExtra(VidChatConsts.EXTRA_AECDUMP_ENABLED, aecDump)
            intent.putExtra(VidChatConsts.EXTRA_SAVE_INPUT_AUDIO_TO_FILE_ENABLED, saveInputAudioToFile)
            intent.putExtra(VidChatConsts.EXTRA_OPENSLES_ENABLED, useOpenSLES)
            intent.putExtra(VidChatConsts.EXTRA_DISABLE_BUILT_IN_AEC, disableBuiltInAEC)
            intent.putExtra(VidChatConsts.EXTRA_DISABLE_BUILT_IN_AGC, disableBuiltInAGC)
            intent.putExtra(VidChatConsts.EXTRA_DISABLE_BUILT_IN_NS, disableBuiltInNS)
            intent.putExtra(VidChatConsts.EXTRA_DISABLE_WEBRTC_AGC_AND_HPF, disableWebRtcAGCAndHPF)
            intent.putExtra(VidChatConsts.EXTRA_AUDIO_BITRATE, audioStartBitrate)
            intent.putExtra(VidChatConsts.EXTRA_AUDIOCODEC, audioCodec)
            intent.putExtra(VidChatConsts.EXTRA_DISPLAY_HUD, displayHud)
            intent.putExtra(VidChatConsts.EXTRA_TRACING, tracing)
            intent.putExtra(VidChatConsts.EXTRA_ENABLE_RTCEVENTLOG, rtcEventLogEnabled)
            intent.putExtra(VidChatConsts.EXTRA_CMDLINE, VidChatConfig.isDebug)
            intent.putExtra(VidChatConsts.EXTRA_RUNTIME, if (VidChatConfig.isDebug) VidChatConsts.DEFAULT_DEBUG_RUNTIME else 0)
            intent.putExtra(VidChatConsts.EXTRA_DATA_CHANNEL_ENABLED, dataChannelEnabled)
            if (dataChannelEnabled) {
                intent.putExtra(VidChatConsts.EXTRA_ORDERED, ordered)
                intent.putExtra(VidChatConsts.EXTRA_MAX_RETRANSMITS_MS, maxReTrMs)
                intent.putExtra(VidChatConsts.EXTRA_MAX_RETRANSMITS, maxReTr)
                intent.putExtra(VidChatConsts.EXTRA_PROTOCOL, protocol)
                intent.putExtra(VidChatConsts.EXTRA_NEGOTIATED, negotiated)
                intent.putExtra(VidChatConsts.EXTRA_ID, id)
            }
        }

        return intent
    }

    fun requestVideoChatPermissions(activity: Activity,requestCode: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // Dynamic permissions are not required before Android M.
            return
        }

        activity.requestPermissions(getVideoCallPermissions(), requestCode)
    }

    fun isPermissionsAllowed(activity: Activity): Boolean {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // Dynamic permissions are not required before Android M.
            return true
        }

        for (item in permissions) {
            if (activity.checkSelfPermission(item) != PackageManager.PERMISSION_GRANTED){
               return false
            }
        }

        return true
    }

    fun getVideoCallPermissions(): Array<String> {
        return permissions
    }
}