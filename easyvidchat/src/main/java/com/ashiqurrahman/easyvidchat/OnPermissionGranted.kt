package com.ashiqurrahman.easyvidchat

import android.content.Intent

/* Created by ashiq.buet16 **/

interface InitVidChatIntentListener {
    fun onPermissionGranted(intent: Intent?);
}