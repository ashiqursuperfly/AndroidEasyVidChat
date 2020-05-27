package com.ashiqurrahman.easyvidchat.rtc_util

import android.app.Activity
import android.content.DialogInterface
import android.webkit.URLUtil
import com.ashiqurrahman.easyvidchat.R
import com.ashiqurrahman.easyvidchat.ui.UiUtil


/* Created by ashiq.buet16 **/

object UrlValidator {

    fun validateUrl(url: String, context: Activity): Boolean {
        if (URLUtil.isHttpsUrl(url) || URLUtil.isHttpUrl(url)) {
            return true
        }

        UiUtil.showAlertDialog(context,
            "Invalid Url",
            context.getString(R.string.invalid_url_text, url),
            "OK"
        ) { dialog: DialogInterface, _: Int ->
            dialog.cancel()
        }
        return false
    }
}