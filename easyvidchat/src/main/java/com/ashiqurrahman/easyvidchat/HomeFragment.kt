/*
package com.ashiqurrahman.apprtc.ui

import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import com.ashiqur.androidbaselib.base.BaseFragment
import com.ashiqurrahman.easyvidchat.util.PrefUtil
import com.ashiqur.androidbaselib.util.helper.Toaster
import com.ashiqurrahman.apprtc.R
import com.ashiqurrahman.apprtc.data.Const
import com.ashiqurrahman.apprtc.util.PrefIntentUtil.sharedPrefGetBoolean
import com.ashiqurrahman.apprtc.util.PrefIntentUtil.sharedPrefGetInteger
import com.ashiqurrahman.apprtc.util.PrefIntentUtil.sharedPrefGetString
import com.ashiqurrahman.apprtc.util.UrlValidator.validateUrl
import kotlinx.android.synthetic.main.fragment_home.*
import timber.log.Timber
import java.util.*

*/
/* Created by ashiq.buet16 **//*


class HomeFragment : BaseFragment() {

    private var commandLineRun = false

    override fun afterOnViewCreated() {
        initViews()
        requestPermissions()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    private fun initViews() {
        room_edittext.requestFocus()
        connect_button.setOnClickListener {
            connectToRoom(room_edittext.text.toString())
        }
    }

    private fun connectToRoom(roomId: String,
        commandLineRun: Boolean  = false,
        useValuesFromIntent: Boolean = false,
        runTimeMs: Int = 0
    ) {
        this.commandLineRun = commandLineRun

        val roomUrl :String = PrefUtil.get(getString(R.string.pref_room_server_url_key), getString(R.string.pref_room_server_url_default)) ?: getString(R.string.pref_room_server_url_default)

        // Video call enabled flag.
        val videoCallEnabled: Boolean = sharedPrefGetBoolean(
            requireActivity(),
            R.string.pref_videocall_key,
            Const.EXTRA_VIDEO_CALL,
            R.string.pref_videocall_default,
            useValuesFromIntent
        )
        // Use screencapture option.
        val useScreencapture: Boolean = sharedPrefGetBoolean(
            requireActivity(),
            R.string.pref_screencapture_key,
            Const.EXTRA_SCREENCAPTURE,
            R.string.pref_screencapture_default,
            useValuesFromIntent
        )
        // Use Camera2 option.
        val useCamera2: Boolean = sharedPrefGetBoolean(
            requireActivity(),
            R.string.pref_camera2_key, Const.EXTRA_CAMERA2,
            R.string.pref_camera2_default, useValuesFromIntent
        )

        // Get default codecs.
        val videoCodec: String = sharedPrefGetString(
            requireActivity(),
            R.string.pref_videocodec_key,
            Const.EXTRA_VIDEOCODEC,
            R.string.pref_videocodec_default,
            useValuesFromIntent
        )
        val audioCodec: String = sharedPrefGetString(
            requireActivity(),
            R.string.pref_audiocodec_key,
            Const.EXTRA_AUDIOCODEC,
            R.string.pref_audiocodec_default,
            useValuesFromIntent
        )

        // Check HW codec flag.
        val hwCodec: Boolean = sharedPrefGetBoolean(
            requireActivity(),
            R.string.pref_hwcodec_key,
            Const.EXTRA_HWCODEC_ENABLED,
            R.string.pref_hwcodec_default,
            useValuesFromIntent
        )

        // Check Capture to texture.
        val captureToTexture = sharedPrefGetBoolean(
            requireActivity(),
            R.string.pref_capturetotexture_key,
            Const.EXTRA_CAPTURETOTEXTURE_ENABLED, R.string.pref_capturetotexture_default,
            useValuesFromIntent
        )
        // Check FlexFEC.
        val flexfecEnabled = sharedPrefGetBoolean(
            requireActivity(),
            R.string.pref_flexfec_key,
            Const.EXTRA_FLEXFEC_ENABLED,
            R.string.pref_flexfec_default,
            useValuesFromIntent
        )

        // Check Disable Audio Processing flag.
        val noAudioProcessing = sharedPrefGetBoolean(
            requireActivity(),
            R.string.pref_noaudioprocessing_key,
            Const.EXTRA_NOAUDIOPROCESSING_ENABLED, R.string.pref_noaudioprocessing_default,
            useValuesFromIntent
        )
        val aecDump = sharedPrefGetBoolean(
            requireActivity(),
            R.string.pref_aecdump_key,
            Const.EXTRA_AECDUMP_ENABLED,
            R.string.pref_aecdump_default,
            useValuesFromIntent
        )
        val saveInputAudioToFile = sharedPrefGetBoolean(
            requireActivity(),
            R.string.pref_enable_save_input_audio_to_file_key,
            Const.EXTRA_SAVE_INPUT_AUDIO_TO_FILE_ENABLED,
            R.string.pref_enable_save_input_audio_to_file_default, useValuesFromIntent
        )

        // Check OpenSL ES enabled flag.
        val useOpenSLES = sharedPrefGetBoolean(
            requireActivity(),
            R.string.pref_opensles_key,
            Const.EXTRA_OPENSLES_ENABLED, R.string.pref_opensles_default, useValuesFromIntent
        )

        // Check Disable built-in AEC flag.
        val disableBuiltInAEC = sharedPrefGetBoolean(
            requireActivity(),
            R.string.pref_disable_built_in_aec_key,
            Const.EXTRA_DISABLE_BUILT_IN_AEC, R.string.pref_disable_built_in_aec_default,
            useValuesFromIntent
        )
        // Check Disable built-in AGC flag.
        val disableBuiltInAGC = sharedPrefGetBoolean(
            requireActivity(),
            R.string.pref_disable_built_in_agc_key,
            Const.EXTRA_DISABLE_BUILT_IN_AGC, R.string.pref_disable_built_in_agc_default,
            useValuesFromIntent
        )

        // Check Disable built-in NS flag.
        val disableBuiltInNS = sharedPrefGetBoolean(
            requireActivity(),
            R.string.pref_disable_built_in_ns_key,
            Const.EXTRA_DISABLE_BUILT_IN_NS, R.string.pref_disable_built_in_ns_default,
            useValuesFromIntent
        )

        // Check Disable gain control
        val disableWebRtcAGCAndHPF = sharedPrefGetBoolean(
            requireActivity(),
            R.string.pref_disable_webrtc_agc_and_hpf_key,
            Const.EXTRA_DISABLE_WEBRTC_AGC_AND_HPF,
            R.string.pref_disable_webrtc_agc_and_hpf_key,
            useValuesFromIntent
        )

        // Get video resolution from settings.
        var videoWidth = 0
        var videoHeight = 0
        if (useValuesFromIntent) {
            videoWidth = requireActivity().intent.getIntExtra(Const.EXTRA_VIDEO_WIDTH, 0)
            videoHeight = requireActivity().intent.getIntExtra(Const.EXTRA_VIDEO_HEIGHT, 0)
        }
        if (videoWidth == 0 && videoHeight == 0) {
            val resolution: String? =
                PrefUtil.get(getString(R.string.pref_resolution_key), getString(R.string.pref_resolution_default))
            val dimensions = resolution?.split("[ x]+")?.toTypedArray()
            if (dimensions?.size == 2) {
                try {
                    videoWidth = dimensions[0].toInt()
                    videoHeight = dimensions[1].toInt()
                } catch (e: NumberFormatException) {
                    Timber.e("Wrong video resolution setting: $resolution")
                }
            }
        }

        // Get camera fps from settings.
        var cameraFps = 0
        if (useValuesFromIntent) {
            cameraFps = requireActivity().intent.getIntExtra(Const.EXTRA_VIDEO_FPS, 0)
        }
        if (cameraFps == 0) {
            val fps: String? =
                PrefUtil.get(getString(R.string.pref_fps_key), getString(R.string.pref_fps_default))
            val fpsValues = fps?.split("[ x]+")?.toTypedArray()
            if (fpsValues?.size == 2) {
                try {
                    cameraFps = fpsValues[0].toInt()
                } catch (e: java.lang.NumberFormatException) {
                    cameraFps = 0
                    Timber.e("Wrong camera fps setting: $fps")
                }
            }
        }

        // Check capture quality slider flag.
        val captureQualitySlider = sharedPrefGetBoolean(
            requireActivity(),
            R.string.pref_capturequalityslider_key,
            Const.EXTRA_VIDEO_CAPTUREQUALITYSLIDER_ENABLED,
            R.string.pref_capturequalityslider_default, useValuesFromIntent
        )
        // Get video and audio start bitrate.
        var videoStartBitrate = 0
        if (useValuesFromIntent) {
            videoStartBitrate = requireActivity().intent.getIntExtra(Const.EXTRA_VIDEO_BITRATE, 0)
        }
        if (videoStartBitrate == 0) {
            val bitrateTypeDefault = getString(R.string.pref_maxvideobitrate_default)
            val bitrateType: String? =
                PrefUtil.get(getString(R.string.pref_maxvideobitrate_key), bitrateTypeDefault)
            if (bitrateType != bitrateTypeDefault) {
                val bitrateValue: String? = PrefUtil.get(
                    getString(R.string.pref_maxvideobitratevalue_key) , getString(R.string.pref_maxvideobitratevalue_default)
                )
                videoStartBitrate = bitrateValue?.toInt()  ?: videoStartBitrate
            }
        }
        var audioStartBitrate = 0
        if (useValuesFromIntent) {
            audioStartBitrate = requireActivity().intent.getIntExtra(Const.EXTRA_AUDIO_BITRATE, 0)
        }
        if (audioStartBitrate == 0) {
            val bitrateTypeDefault =
                getString(R.string.pref_startaudiobitrate_default)
            val bitrateType: String? =
                PrefUtil.get(getString(R.string.pref_startaudiobitrate_key), bitrateTypeDefault)
            if (bitrateType != bitrateTypeDefault) {
                val bitrateValue: String? = PrefUtil.get(
                    getString(R.string.pref_startaudiobitratevalue_key),
                    getString(R.string.pref_startaudiobitratevalue_default)
                )
                audioStartBitrate = bitrateValue?.toInt() ?: audioStartBitrate
            }
        }


        // Check statistics display option.
        val displayHud = sharedPrefGetBoolean(
            requireActivity(),
            R.string.pref_displayhud_key,
            Const.EXTRA_DISPLAY_HUD, R.string.pref_displayhud_default, useValuesFromIntent
        )
        val tracing = sharedPrefGetBoolean(
            requireActivity(),
            R.string.pref_tracing_key,
            Const.EXTRA_TRACING,
            R.string.pref_tracing_default,
            useValuesFromIntent
        )

        // Check Enable RtcEventLog.
        val rtcEventLogEnabled = sharedPrefGetBoolean(
            requireActivity(),
            R.string.pref_enable_rtceventlog_key,
            Const.EXTRA_ENABLE_RTCEVENTLOG, R.string.pref_enable_rtceventlog_default,
            useValuesFromIntent
        )

        // Get datachannel options
        val dataChannelEnabled = sharedPrefGetBoolean(
            requireActivity(),
            R.string.pref_enable_datachannel_key,
            Const.EXTRA_DATA_CHANNEL_ENABLED,
            R.string.pref_enable_datachannel_default,
            useValuesFromIntent
        )
        val ordered = sharedPrefGetBoolean(
            requireActivity(),
            R.string.pref_ordered_key,
            Const.EXTRA_ORDERED,
            R.string.pref_ordered_default,
            useValuesFromIntent
        )
        val negotiated = sharedPrefGetBoolean(
            requireActivity(),
            R.string.pref_negotiated_key,
            Const.EXTRA_NEGOTIATED,
            R.string.pref_negotiated_default,
            useValuesFromIntent
        )
        val maxRetrMs: Int = sharedPrefGetInteger(
            requireActivity(),
            R.string.pref_max_retransmit_time_ms_key,
            Const.EXTRA_MAX_RETRANSMITS_MS, R.string.pref_max_retransmit_time_ms_default,
            useValuesFromIntent
        )
        val maxRetr: Int = sharedPrefGetInteger(
            requireActivity(),
            R.string.pref_max_retransmits_key,
            Const.EXTRA_MAX_RETRANSMITS,
            R.string.pref_max_retransmits_default,
            useValuesFromIntent
        )
        val id: Int = sharedPrefGetInteger(
            requireActivity(),
            R.string.pref_data_id_key,
            Const.EXTRA_ID,
            R.string.pref_data_id_default,
            useValuesFromIntent
        )
        val protocol = sharedPrefGetString(
            requireActivity(),
            R.string.pref_data_protocol_key,
            Const.EXTRA_PROTOCOL,
            R.string.pref_data_protocol_default,
            useValuesFromIntent
        )
        // Start AppRTCMobile activity.
        Timber.d("Connecting to room $roomId at URL $roomUrl")

        if (validateUrl(roomUrl, requireActivity())) {
            val uri = Uri.parse(roomUrl)
            val intent = Intent(requireActivity(), CallActivity::class.java)
            intent.data = uri
            intent.putExtra(Const.EXTRA_ROOMID, roomId)
            intent.putExtra(Const.EXTRA_VIDEO_CALL, videoCallEnabled)
            intent.putExtra(Const.EXTRA_SCREENCAPTURE, useScreencapture)
            intent.putExtra(Const.EXTRA_CAMERA2, useCamera2)
            intent.putExtra(Const.EXTRA_VIDEO_WIDTH, videoWidth)
            intent.putExtra(Const.EXTRA_VIDEO_HEIGHT, videoHeight)
            intent.putExtra(Const.EXTRA_VIDEO_FPS, cameraFps)
            intent.putExtra(
                Const.EXTRA_VIDEO_CAPTUREQUALITYSLIDER_ENABLED,
                captureQualitySlider
            )
            intent.putExtra(Const.EXTRA_VIDEO_BITRATE, videoStartBitrate)
            intent.putExtra(Const.EXTRA_VIDEOCODEC, videoCodec)
            intent.putExtra(Const.EXTRA_HWCODEC_ENABLED, hwCodec)
            intent.putExtra(Const.EXTRA_CAPTURETOTEXTURE_ENABLED, captureToTexture)
            intent.putExtra(Const.EXTRA_FLEXFEC_ENABLED, flexfecEnabled)
            intent.putExtra(Const.EXTRA_NOAUDIOPROCESSING_ENABLED, noAudioProcessing)
            intent.putExtra(Const.EXTRA_AECDUMP_ENABLED, aecDump)
            intent.putExtra(
                Const.EXTRA_SAVE_INPUT_AUDIO_TO_FILE_ENABLED,
                saveInputAudioToFile
            )
            intent.putExtra(Const.EXTRA_OPENSLES_ENABLED, useOpenSLES)
            intent.putExtra(Const.EXTRA_DISABLE_BUILT_IN_AEC, disableBuiltInAEC)
            intent.putExtra(Const.EXTRA_DISABLE_BUILT_IN_AGC, disableBuiltInAGC)
            intent.putExtra(Const.EXTRA_DISABLE_BUILT_IN_NS, disableBuiltInNS)
            intent.putExtra(Const.EXTRA_DISABLE_WEBRTC_AGC_AND_HPF, disableWebRtcAGCAndHPF)
            intent.putExtra(Const.EXTRA_AUDIO_BITRATE, audioStartBitrate)
            intent.putExtra(Const.EXTRA_AUDIOCODEC, audioCodec)
            intent.putExtra(Const.EXTRA_DISPLAY_HUD, displayHud)
            intent.putExtra(Const.EXTRA_TRACING, tracing)
            intent.putExtra(Const.EXTRA_ENABLE_RTCEVENTLOG, rtcEventLogEnabled)
            intent.putExtra(Const.EXTRA_CMDLINE, commandLineRun)
            intent.putExtra(Const.EXTRA_RUNTIME, runTimeMs)
            intent.putExtra(Const.EXTRA_DATA_CHANNEL_ENABLED, dataChannelEnabled)
            if (dataChannelEnabled) {
                intent.putExtra(Const.EXTRA_ORDERED, ordered)
                intent.putExtra(Const.EXTRA_MAX_RETRANSMITS_MS, maxRetrMs)
                intent.putExtra(Const.EXTRA_MAX_RETRANSMITS, maxRetr)
                intent.putExtra(Const.EXTRA_PROTOCOL, protocol)
                intent.putExtra(Const.EXTRA_NEGOTIATED, negotiated)
                intent.putExtra(Const.EXTRA_ID, id)
            }
            if (useValuesFromIntent) {
                if (requireActivity().intent.hasExtra(Const.EXTRA_VIDEO_FILE_AS_CAMERA)) {
                    val videoFileAsCamera: String? =
                        requireActivity().intent.getStringExtra(Const.EXTRA_VIDEO_FILE_AS_CAMERA)
                    intent.putExtra(Const.EXTRA_VIDEO_FILE_AS_CAMERA, videoFileAsCamera)
                }
                if (requireActivity().intent.hasExtra(Const.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE)) {
                    val saveRemoteVideoToFile: String? =
                        requireActivity().intent.getStringExtra(Const.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE)
                    intent.putExtra(
                        Const.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE,
                        saveRemoteVideoToFile
                    )
                }
                if (requireActivity().intent.hasExtra(Const.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_WIDTH)) {
                    val videoOutWidth: Int = requireActivity().intent.getIntExtra(
                        Const.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_WIDTH,
                        0
                    )
                    intent.putExtra(
                        Const.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_WIDTH,
                        videoOutWidth
                    )
                }
                if (requireActivity().intent.hasExtra(Const.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_HEIGHT)) {
                    val videoOutHeight: Int = requireActivity().intent.getIntExtra(
                        Const.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_HEIGHT,
                        0
                    )
                    intent.putExtra(
                        Const.EXTRA_SAVE_REMOTE_VIDEO_TO_FILE_HEIGHT,
                        videoOutHeight
                    )
                }
            }
            startActivityForResult(intent, Const.CONNECTION_REQUEST)
        }
    }

    private fun onPermissionsGranted() {
        //TODO: If an implicit VIEW intent is launching the app, go directly to that URL.
        */
/*val intent: Intent = getIntent()
        if ("android.intent.action.VIEW" == intent.action && !com.someshk.apprtc.ConnectActivity.commandLineRun) {
            val loopback =
                intent.getBooleanExtra(Const.EXTRA_LOOPBACK, false)
            val runTimeMs = intent.getIntExtra(Const.EXTRA_RUNTIME, 0)
            val useValuesFromIntent =
                intent.getBooleanExtra(Const.EXTRA_USE_VALUES_FROM_INTENT, false)
            val room: String = sharedPref.getString(keyprefRoom, "")
            connectToRoom(room, true, loopback, useValuesFromIntent, runTimeMs)
        }*//*

    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // Dynamic permissions are not required before Android M.
            onPermissionsGranted()
            return
        }
        val missingPermissions = getMissingPermissions()
        if (missingPermissions.isNotEmpty()) {
            requestPermissions(
                missingPermissions,
                Const.PERMISSION_REQUEST
            )
        } else {
            onPermissionsGranted()
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun getMissingPermissions(): Array<String?> {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return arrayOfNulls(0)
        }
        val info: PackageInfo = try {
            requireActivity().packageManager.getPackageInfo(
                requireActivity().packageName,
                PackageManager.GET_PERMISSIONS
            )
        } catch (e: PackageManager.NameNotFoundException) {
            Timber.w(
                "Failed to retrieve permissions."
            )
            return arrayOfNulls(0)
        }
        if (info.requestedPermissions == null) {
            Timber.w("No requested permissions.")
            return arrayOfNulls(0)
        }
        val missingPermissions =
            ArrayList<String?>()
        for (i in info.requestedPermissions.indices) {
            if (info.requestedPermissionsFlags[i] and PackageInfo.REQUESTED_PERMISSION_GRANTED == 0) {
                missingPermissions.add(info.requestedPermissions[i])
            }
        }
        Timber.d("Missing permissions: $missingPermissions")
        return missingPermissions.toTypedArray()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == Const.CONNECTION_REQUEST && commandLineRun) {
            Timber.d("Return: $resultCode")
            commandLineRun = false
            //setResult(resultCode)
            Toaster.showToast("OnActivityResult, Code:$resultCode")
        }
    }

}*/
