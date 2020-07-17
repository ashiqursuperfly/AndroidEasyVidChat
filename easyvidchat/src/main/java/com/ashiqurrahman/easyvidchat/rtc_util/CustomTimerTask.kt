package com.ashiqurrahman.easyvidchat.rtc_util

import java.util.*


/*
 * Created by : 
 * <a href="https://www.github.com/ashiqursuperfly">Ashiqur Rahman</a> on 7/17/20.
*/

class CustomTimerTask: TimerTask() {
    private var mCallback: TimerCallback? = null

    fun setCallback(callback: TimerCallback) {
        mCallback = callback;
    }

    override fun run() {
        mCallback?.onTime()
    }

    interface TimerCallback {
        fun onTime()
    }
}