# Easy Android Video Call [![](https://jitpack.io/v/ashiqursuperfly/AndroidEasyVidChat.svg)](https://jitpack.io/#ashiqursuperfly/AndroidEasyVidChat)
Add video calling to your android application using just a few lines of code

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
## Advanced Usage
Please go through the [Wiki](https://github.com/ashiqursuperfly/AndroidEasyVidChat/wiki/Advanced-Usage) for a list of advanced options 

## Contributing
- Create an issue with appropriate description (explain which behaviour you want to change)
- Add either one of the following labels: bug, enhancement, documentation, question
- Prefix the branch name with the issue label and issue number like so: label/number-branchName e.g: bug/3-Hour24ViewNotVisible
- send a PR

#### This code makes use of this <a href="https://appr.tc/">https://appr.tc/</a> and some of the source codes in <a href="https://chromium.googlesource.com/">chromium.googlesource</a>

