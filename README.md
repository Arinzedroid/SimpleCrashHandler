# SimpleCrashHandler
A crash handler library for android development 

[![](https://jitpack.io/v/Arinzedroid/SimpleCrashHandler.svg)](https://jitpack.io/#Arinzedroid/SimpleCrashHandler)

# Dev Tool
This library is written with kotlin but it can be used in both kotlin and java projects.

# Usage
At the root project build.gradle file, add below text
```
	allprojects {
		repositories {
		
			maven { url 'https://jitpack.io' }
		}
	}
```
Then in the module build.gradle add below text to download lib
```
implementation 'com.github.Arinzedroid:SimpleCrashHandler:1.0.1'

```
After downloading, move to your application class and initialize the library. 
It is important the library is initialized in the application class, this is because the lib needs the application context for proper funtioning. 

# Kotlin

```kotlin
SimpleCrashHandler.Init(this)
```
Thats all that is needed. The library handles crashes in your app by smootly restarts the activity that crashes. 
You can test the magic of this lib by simply intentionally crashing your app at runtime. Use below code 

```kotlin
button.setOnClickListener{
  throw RuntimeException("$this dummy Error")
}
```
You can also init this lib with a configuration. Start by creating a configuration in application class from config.
```kotlin
  val config = Config(this)
  config.enableNotification = true
  config.notificationMsg = "Dummy notification of crash"
  config.handler = Thread.getDefaultUncaughtExceptionHandler()
  config.startActivity = MainActivity()
  SimpleCrashHandler.Init(config)
```
the startActivity is the activity to start when a crash occurs. 
In the start activity get the crashReason with below code. In the activity's onCreate,

```kotlin
val data = intent.getStringExtra(CRASH_DATA)
println("Crash Reason $data")
```
# Update
Pushing crash reason to a remote server specified by developer for easier debugging.

