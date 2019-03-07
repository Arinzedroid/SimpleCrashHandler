package dev.arinzedroid.simplecrashhandler

import android.app.Activity
import android.app.Application

data class Config constructor(
    var enableNotification: Boolean = false,
    var notificationMsg: String = "",
    var app: Application,
    var startActivity: Activity? = null,
    var handler: Thread.UncaughtExceptionHandler? = null
){
    constructor(application: Application): this(app = application)
}