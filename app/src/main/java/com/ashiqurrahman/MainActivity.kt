package com.ashiqurrahman

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.ashiqurrahman.easyvidchat.VidChat
import com.ashiqurrahman.easyvidchat.data.CustomTURNSTUNConfig
import com.ashiqurrahman.easyvidchat.data.VidChatConfig
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val CALL_REQUEST_CODE = 1231
    val PERMISSION_REQUEST_CODE = 1232

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        VidChatConfig.AlertDialogUI.alertDialogBgColorRes = R.color.colorPrimary
        VidChatConfig.AlertDialogUI.btnBgColorRes = R.color.colorPrimaryDark
        VidChatConfig.AlertDialogUI.iconDrawableRes = R.drawable.ic_launcher_foreground
        VidChatConfig.AlertDialogUI.btnTextColorRes = R.color.colorAccent
        VidChatConfig.AlertDialogUI.textMsgColorHexSixDigitString = "#86b0a3"
        VidChatConfig.AlertDialogUI.titleTextColorHexSixDigitString = "#FFFFFF"
        // VidChatConfig.CustomErrorMsg.errorTitle = getString(R.string.error_bangla_title)
        // VidChatConfig.CustomErrorMsg.errorMessage = getString(R.string.error_bangla_body)

        VidChatConfig.CustomButton.customBtnIcon = R.drawable.ic_launcher_foreground
        VidChatConfig.CustomButton.customBtnListener = View.OnClickListener {
            Toast.makeText(this, "Clicked Custom button", Toast.LENGTH_LONG).show()
        }

        val customTURNSTUNConfig = CustomTURNSTUNConfig(
            turnServerUrl = "turn:103.147.168.11:3478",
            stunServerUrl = "stun:103.147.168.11:3478",
            username = "askdoctorbd",
            credential = "swot24434"
        )

        VidChatConfig.customRoomServerUrl = "https://103.147.168.11:8081"
        VidChatConfig.customTURNSTUNConfig = customTURNSTUNConfig

        VidChat.requestVideoChatPermissions(this, PERMISSION_REQUEST_CODE)

        btn_call.setOnClickListener {
            startActivityForResult(VidChat.getCallingIntent(this, et_room.text.toString()),CALL_REQUEST_CODE)
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (!VidChat.isPermissionsAllowed(this)) {
            Toast.makeText(this, "Error: Please Provide Videochat permissions !!", Toast.LENGTH_SHORT).show()
            finish()
        }

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
