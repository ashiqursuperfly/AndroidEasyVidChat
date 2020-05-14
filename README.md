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



