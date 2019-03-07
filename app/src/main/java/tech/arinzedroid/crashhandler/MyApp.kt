package tech.arinzedroid.crashhandler

import android.app.Application
import dev.arinzedroid.simplecrashhandler.Config
import dev.arinzedroid.simplecrashhandler.SimpleCrashHandler

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initHandler()
    }

    private fun initHandler() {
        val config = Config(this)
        config.enableNotification = true
        config.notificationMsg = "Dummy notification of crash"
        config.handler = Thread.getDefaultUncaughtExceptionHandler()
        config.startActivity = Main2Activity()
        SimpleCrashHandler.Init(config)
    }

}
