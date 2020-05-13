package com.ashiqurrahman.easyvidchat.util


import android.app.Activity
import android.util.Log
import androidx.preference.PreferenceManager



/* Created by ashiq.buet16 **/

object PrefIntentUtil {

    /**
     * Get a value from the shared preference or from the intent, if it does not
     * exist the default is used.
     */
    fun sharedPrefGetString(
        activity: Activity, attributeId: Int, intentName: String, defaultId: Int, useFromIntent: Boolean
    ): String {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)
        val defaultValue: String = activity.getString(defaultId)
        return if (useFromIntent) {
            val value: String? = activity.intent.getStringExtra(intentName)
            value ?: defaultValue
        } else {
            val attributeName: String = activity.getString(attributeId)
            sharedPref.getString(attributeName, defaultValue) ?:defaultValue
        }
    }

    /**
     * Get a value from the shared preference or from the intent, if it does not
     * exist the default is used.
     */
    fun sharedPrefGetBoolean(
        activity: Activity,attributeId: Int, intentName: String, defaultId: Int, useFromIntent: Boolean
    ): Boolean {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)
        val defaultValue = java.lang.Boolean.parseBoolean(activity.getString(defaultId))
        return if (useFromIntent) {
            activity.getIntent().getBooleanExtra(intentName, defaultValue)
        } else {
            val attributeName: String = activity.getString(attributeId)
            sharedPref.getBoolean(attributeName, defaultValue)
        }
    }

    /**
     * Get a value from the shared preference or from the intent, if it does not
     * exist the default is used.
     */
    fun sharedPrefGetInteger(activity: Activity, attributeId: Int, intentName: String, defaultId: Int, useFromIntent: Boolean): Int {

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)
        val defaultString: String = activity.getString(defaultId)
        val defaultValue = defaultString.toInt()
        return if (useFromIntent) {
            activity.intent.getIntExtra(intentName, defaultValue)
        } else {
            val attributeName: String = activity.getString(attributeId)
            val value: String = sharedPref.getString(attributeName, defaultString).toString()
            try {
                value.toInt()
            } catch (e: NumberFormatException) {
                Log.e("sharedPrefGetInteger()","Wrong setting for: $attributeName:$value")
                defaultValue
            }
        }
    }


}