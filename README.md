# Easy Android Video Call [![](https://jitpack.io/v/ashiqursuperfly/AndroidEasyVidChat.svg)](https://jitpack.io/#ashiqursuperfly/AndroidEasyVidChat)
Add video calling to your android application using just a few lines of code

## Download the sample app
<a href='https://play.google.com/store/apps/details?id=com.ashiqurrahman.apprtc&hl=en&pcampaignid=pcampaignidMKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png' height="80" width="206" /></a>

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
- Add dependency in app-level build.gradle
```groovy
implementation 'com.github.ashiqursuperfly:AndroidEasyVidChat:x.x.x'
```
Make sure you use the latest release version, this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).
- Add this in manifest
```xml
<activity android:name="com.ashiqurrahman.easyvidchat.ui.CallActivity" />
```

## Usage
- request required permissions
```kotlin
VidChat.requestVideoChatPermissions( this, PERMISSION_REQUEST_CODE)
```
- after the user has accepted all the permissions, just a **single line of code** initiates video calling intent
```kotlin
startActivityForResult(VidChat.getCallingIntent(activity, roomID), CALL_REQUEST_CODE) // send an unique roomID for your call, the receiver user must also connect on the same roomID
```
- similarly if you want screen sharing
```kotlin
VidChatConfig.screenCaptureEnabled = true
startActivityForResult(VidChat.getCallingIntent(activity, roomID), CALL_REQUEST_CODE) // send an unique roomID for your call, the receiver user must also connect on the same roomID
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
                Toast.makeText(this, "Call Success, OnActivityResult, Code:$resultCode", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Call Failed/Disconnected, OnActivityResult, Code:$resultCode", Toast.LENGTH_LONG).show()
            }
        }
    }
```
## Wiki
Please go through the [Wiki](https://github.com/ashiqursuperfly/AndroidEasyVidChat/wiki) for **advanced usages**, **contributing guide** and more. 

This project makes use of the <a href="https://appr.tc/">apprtc project</a> and some of the source codes in <a href="https://chromium.googlesource.com/">chromium.googlesource</a>

