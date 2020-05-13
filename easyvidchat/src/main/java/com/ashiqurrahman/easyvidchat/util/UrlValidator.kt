package com.ashiqurrahman.easyvidchat.util

import android.app.Activity
import android.app.AlertDialog
import android.webkit.URLUtil
import com.ashiqurrahman.easyvidchat.R


/* Created by ashiq.buet16 **/

object UrlValidator {

    fun validateUrl(url: String, context: Activity): Boolean {
        if (URLUtil.isHttpsUrl(url) || URLUtil.isHttpUrl(url)) {
            return true
        }
        AlertDialog.Builder(context)
            .setTitle("Invalid Url")
            .setMessage(context.getString(R.string.invalid_url_text, url))
            .setCancelable(false)
            .setNeutralButton("OK"
            ) { dialog, _ -> dialog.cancel() }
            .create()
            .show()
        return false
    }
}