# TODO
- Make sure you are not violating licences for the google files
- 

## Setup
- Add dependency in build.gradle
- Add this in app-level build.gradle inside android {}
```
compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
}
kotlinOptions {
    jvmTarget = JavaVersion.VERSION_1_8.toString()
}
  ```
- Add this in manifest
```
<activity android:name=".easyvidchat.ui.CallActivity" />
```
