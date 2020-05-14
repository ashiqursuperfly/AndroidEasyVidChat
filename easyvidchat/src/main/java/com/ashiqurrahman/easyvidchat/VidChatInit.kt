package com.ashiqurrahman.easyvidchat

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.preference.PreferenceManager
import com.ashiqurrahman.easyvidchat.data.VidChatConsts
import com.ashiqurrahman.easyvidchat.data.VidChatConfig
import com.ashiqurrahman.easyvidchat.ui.CallActivity
import com.ashiqurrahman.easyvidchat.util.PrefUtil
import com.ashiqurrahman.easyvidchat.util.UrlValidator.validateUrl

/* Created by ashiq.buet16 **/

object VidChatInit {

    const val TAG = "VidChatInit"

    fun initDefaultSettings(context: Context) {
        PrefUtil.init(context)
        PreferenceManager.setDefaultValues(context, R.xml.shared_pref_defaults, false)
    }


    private fun getIntent(
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

    @TargetApi(Build.VERSION_CODES.M)
    fun checkPermissionsAndGetVdoCallIntent(roomID: String, activity: Activity, listener: InitVidChatIntentListener) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // Dynamic permissions are not required before Android M.
            listener.onPermissionGranted(getIntent(activity = activity,roomID =  roomID))
            return
        }
        val missingPermissions = getMissingPermissions(activity)
        if (missingPermissions.isNotEmpty()) {
            activity.requestPermissions(
                missingPermissions,
                VidChatConsts.PERMISSION_REQUEST
            )
            listener.onPermissionGranted(getIntent(activity = activity,roomID =  roomID))
        } else {
            listener.onPermissionGranted(getIntent(activity = activity,roomID =  roomID))
        }
    }

    private fun getMissingPermissions(activity: Activity): Array<String?> {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return arrayOfNulls(0)
        }
        val info: PackageInfo = try {
            activity.packageManager.getPackageInfo(
                activity.packageName,
                PackageManager.GET_PERMISSIONS
            )
        } catch (e: PackageManager.NameNotFoundException) {
            Log.w(TAG,"Failed to retrieve permissions.")
            return arrayOfNulls(0)
        }
        if (info.requestedPermissions == null) {
            Log.w(TAG,"No requested permissions.")
            return arrayOfNulls(0)
        }
        val missingPermissions = ArrayList<String?>()

        for (i in info.requestedPermissions.indices) {
            if (info.requestedPermissionsFlags[i] and PackageInfo.REQUESTED_PERMISSION_GRANTED == 0) {
                missingPermissions.add(info.requestedPermissions[i])
            }
        }
        Log.d(TAG, "Missing permissions: $missingPermissions")
        return missingPermissions.toTypedArray()
    }


}