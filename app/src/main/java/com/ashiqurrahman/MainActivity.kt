package com.ashiqurrahman

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ashiqurrahman.easyvidchat.InitVidChatIntentListener
import com.ashiqurrahman.easyvidchat.VidChatInit


class MainActivity : AppCompatActivity() {

    val CALL_REQUEST_CODE = 1231;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        VidChatInit.checkPermissionsAndGetVdoCallIntent("16051031605", this,
            object: InitVidChatIntentListener{
                override fun onPermissionGranted(intent: Intent?) {
                    if(intent == null){
                        Toast.makeText(applicationContext, "Please Allow Permissions First", Toast.LENGTH_SHORT).show()
                        return
                    }
                    startActivityForResult(intent,CALL_REQUEST_CODE)
                }
            }
        )
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
