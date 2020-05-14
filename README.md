## Setup
- Add jitpack in project-level build.gradle
```groovy
allprojects {
	repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }
}
```

- Add dependency in app-level build.gradle
```groovy
implementation 'com.github.ashiqursuperfly:AndroidEasyVidChat:1.0.0'
```
- Add this in app-level build.gradle inside android {}
```groovy
compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
}
kotlinOptions {
    jvmTarget = JavaVersion.VERSION_1_8.toString()
}
  ```
- Add this in manifest
```xml
<activity android:name=".easyvidchat.ui.CallActivity" />
```

## Usage
- request required permissions
```kotlin
VidChat.requestVideoChatPermissions( this, PERMISSION_REQUEST_CODE)
```
- after the user has accepted all the permissions, just a **single line of code** initiates video calling intent
```kotlin
startActivityForResult(VidChat.getCallingIntent(this, roomID), CALL_REQUEST_CODE) // send an unique roomID for your call, the receiver user must also connect on the same roomID
```
- receive results after the call is disconnected
```kotlin
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
```


### This code makes use of this <a href="https://appr.tc/">https://appr.tc/</a> and some of the source codes in <a href="https://chromium.googlesource.com/">chromium.googlesource</a>

