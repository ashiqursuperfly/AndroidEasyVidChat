/*
 *  Copyright 2015 The WebRTC Project Authors. All rights reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */

package com.ashiqurrahman.easyvidchat.ui;

import android.app.Activity;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.ashiqurrahman.easyvidchat.R;
import com.ashiqurrahman.easyvidchat.data.VidChatConfig;
import com.ashiqurrahman.easyvidchat.data.VidChatConsts;
import com.ashiqurrahman.easyvidchat.rtc_util.CallDurationUtil;
import com.ashiqurrahman.easyvidchat.rtc_util.CaptureQualityController;
import com.ashiqurrahman.easyvidchat.rtc_util.CustomTimerTask;
import com.ashiqurrahman.easyvidchat.rtc_util.OnCallEvents;

import org.webrtc.RendererCommon.ScalingType;

import java.util.Timer;

/**
 * Fragment for call control.
 */
public class CallFragment extends Fragment {
    private TextView contactView;
    private ImageButton disconnectButton;
    private ImageButton cameraSwitchButton;
    private ImageButton customButton;
    private ImageButton toggleMuteButton;
    private TextView captureFormatText;
    private SeekBar captureFormatSlider;
    public OnCallEvents callEvents;
    private ScalingType scalingType;
    private boolean videoCallEnabled = true;
    private long mCallStartTimeStamp = -1;
    private Timer callDurationTimer;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View controlView = inflater.inflate(R.layout.fragment_call, container, false);
        // Create UI controls.
        contactView = controlView.findViewById(R.id.contact_name_call);
        disconnectButton = controlView.findViewById(R.id.button_call_disconnect);
        cameraSwitchButton = controlView.findViewById(R.id.button_call_switch_camera);
        customButton = controlView.findViewById(R.id.button_custom);
        toggleMuteButton = controlView.findViewById(R.id.button_call_toggle_mic);
        captureFormatText = controlView.findViewById(R.id.capture_format_text_call);
        captureFormatSlider = controlView.findViewById(R.id.capture_format_slider_call);
        // Add buttons click events.
        initDrawables();
        disconnectButton.setOnClickListener(view -> callEvents.onCallHangUp());
        cameraSwitchButton.setOnClickListener(view -> callEvents.onCameraSwitch());

        /*videoScalingButton.setVisibility(View.GONE);
        videoScalingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scalingType == ScalingType.SCALE_ASPECT_FILL) {
                    videoScalingButton.setBackgroundResource(R.drawable.ic_fullscreen);
                    scalingType = ScalingType.SCALE_ASPECT_FIT;
                } else {
                    videoScalingButton.setBackgroundResource(R.drawable.ic_fullscreen_exit);
                    scalingType = ScalingType.SCALE_ASPECT_FILL;
                }
                callEvents.onVideoScalingSwitch(scalingType);
            }
        });
        scalingType = ScalingType.SCALE_ASPECT_FILL;
        */

        toggleMuteButton.setOnClickListener(view -> {
            boolean enabled = callEvents.onToggleMic();
            toggleMuteButton.setAlpha(enabled ? 1.0f : 0.3f);
        });
        return controlView;
    }

    @Override
    public void onStart() {
        super.onStart();
        boolean captureSliderEnabled = false;
        Bundle args = getArguments();
        if (args != null) {
            String contactName = args.getString(VidChatConsts.EXTRA_ROOMID);
            contactView.setText(contactName);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                contactView.setTextColor(getActivity().getColor(VidChatConfig.AlertDialogUI.INSTANCE.getBtnBgColorRes()));
            }
            videoCallEnabled = args.getBoolean(VidChatConsts.EXTRA_VIDEO_CALL, true);
            captureSliderEnabled = videoCallEnabled
                    && args.getBoolean(VidChatConsts.EXTRA_VIDEO_CAPTUREQUALITYSLIDER_ENABLED, false);
        }
        if (!videoCallEnabled) {
            cameraSwitchButton.setVisibility(View.INVISIBLE);
        }
        if (captureSliderEnabled) {
            captureFormatSlider.setOnSeekBarChangeListener(
                    new CaptureQualityController(captureFormatText, callEvents));
        } else {
            captureFormatText.setVisibility(View.GONE);
            captureFormatSlider.setVisibility(View.GONE);
        }

        if(mCallStartTimeStamp == -1)mCallStartTimeStamp = System.currentTimeMillis();
        CustomTimerTask callDurationPeriodicTask = new CustomTimerTask();
        callDurationPeriodicTask.setCallback(
                () -> {
                    String s = CallDurationUtil.convertToFormattedString(System.currentTimeMillis()-mCallStartTimeStamp);
                    getActivity().runOnUiThread(
                            () -> contactView.setText(s)
                    );
                }
        );
        callDurationTimer = new Timer();
        callDurationTimer.scheduleAtFixedRate(callDurationPeriodicTask, 2000L, 1000L);

    }

    // TODO(sakal): Replace with onAttach(Context) once we only support API level 23+.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callEvents = (OnCallEvents) activity;
    }

    private void initDrawables() {
       disconnectButton.setBackgroundResource(VidChatConfig.VidChatIcons.INSTANCE.getDisconnectIcon());
       cameraSwitchButton.setBackgroundResource(VidChatConfig.VidChatIcons.INSTANCE.getSwitchCameraIcon());
       toggleMuteButton.setBackgroundResource(VidChatConfig.VidChatIcons.INSTANCE.getMicIcon());

       if (VidChatConfig.CustomButton.INSTANCE.getCustomBtnListener() != null) {
          customButton.setBackgroundResource(VidChatConfig.CustomButton.INSTANCE.getCustomBtnIcon());
          customButton.setOnClickListener(VidChatConfig.CustomButton.INSTANCE.getCustomBtnListener());
          customButton.setVisibility(View.VISIBLE);
       }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(callDurationTimer != null)callDurationTimer.cancel();
    }
}
