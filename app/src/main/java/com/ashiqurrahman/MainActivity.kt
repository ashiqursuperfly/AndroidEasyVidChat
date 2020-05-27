package com.ashiqurrahman

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ashiqurrahman.easyvidchat.VidChat
import com.ashiqurrahman.easyvidchat.data.VidChatConfig
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val CALL_REQUEST_CODE = 1231;
    val PERMISSION_REQUEST_CODE = 1232;
    lateinit var activity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        VidChatConfig.AlertDialogUI.alertDialogBgColorRes = R.color.colorPrimary
        VidChatConfig.AlertDialogUI.btnBgColorRes = R.color.colorPrimaryDark
        VidChatConfig.AlertDialogUI.iconDrawableRes = R.drawable.ic_mic
        VidChatConfig.AlertDialogUI.btnTextColorRes = R.color.colorPrimary
        VidChatConfig.AlertDialogUI.textMsgColorHexSixDigitString = "#FFFFFF"
        VidChatConfig.AlertDialogUI.titleTextColorHexSixDigitString = "#FFFFFF"

        VidChat.requestVideoChatPermissions(this, PERMISSION_REQUEST_CODE)
        activity = this

        btn_call.setOnClickListener {
            startActivityForResult(VidChat.getCallingIntent(activity, et_room.text.toString()),CALL_REQUEST_CODE)
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        for (item in grantResults)
            Log.d(this.localClassName,"Denied Permission: $item")
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == CALL_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "CALL Success, OnActivityResult, Code:$resultCode", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "CALL Failed, OnActivityResult, Code:$resultCode", Toast.LENGTH_LONG).show()
            }
        }
    }
}
