package com.ashiqurrahman

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ashiqurrahman.easyvidchat.VidChat
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    private val mRoomID: String = "103103127"
    val CALL_REQUEST_CODE = 1231;
    val PERMISSION_REQUEST_CODE = 1232;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        VidChat.requestVideoChatPermissions( this, PERMISSION_REQUEST_CODE)
        startActivityForResult(VidChat.getCallingIntent(this, mRoomID),CALL_REQUEST_CODE)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var allPermissionsGranted = true
        for (item in grantResults)
            if (item != PackageManager.PERMISSION_GRANTED) allPermissionsGranted = false

        if (allPermissionsGranted)
            startActivityForResult(VidChat.getCallingIntent(this, mRoomID), CALL_REQUEST_CODE)
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
