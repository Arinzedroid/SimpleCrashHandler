package dev.arinzedroid

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.os.Build
import android.util.Log


const val CRASH_DATA = "crash_data"

abstract class SimpleCrashHandler{

    companion object {
        @JvmStatic
        fun Init(app: Application){
            val t = Thread.getDefaultUncaughtExceptionHandler()
            val appHandler = ExceptionHandler(app,t)
            app.registerActivityLifecycleCallbacks(appHandler)
            Thread.setDefaultUncaughtExceptionHandler(appHandler)
        }

        @JvmStatic
        fun Init(config: Config){
          if(config.handler == null){
              config.handler = Thread.getDefaultUncaughtExceptionHandler()
          }
            val app = config.app
            val appHandler = ExceptionHandler2(config)
            app.registerActivityLifecycleCallbacks(appHandler)
            Thread.setDefaultUncaughtExceptionHandler(appHandler)
        }
    }
}

private class ExceptionHandler2(private val config: Config):Thread.UncaughtExceptionHandler,
    Application.ActivityLifecycleCallbacks{

    private var hasActivity: Boolean = true

    override fun uncaughtException(t: Thread?, e: Throwable?) {
        val data = extractCrashData(t,e)

        if(config.enableNotification){
            NotificationUtil(config.app).createNotification(config.notificationMsg)
        }

        if(config.startActivity != null){
            val intent = Intent(config.app.applicationContext,config.startActivity?.javaClass)
            intent.putExtra(CRASH_DATA,data)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            config.app.startActivity(intent)

            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(10)

        }else{
            println("activity is null")
            config.handler?.uncaughtException(t,e)

        }
    }

    private fun extractCrashData(t: Thread?, e: Throwable?): String {
        var arr = e?.stackTrace
        val DOUBLE_LINE_SEP = "\n\n"
        val SINGLE_LINE_SEP = "\n"
        val report = StringBuilder(e.toString())
        val lineSeperator = "-------------------------------\n\n"
        report.append(DOUBLE_LINE_SEP)
        report.append("--------- Stack trace ---------\n\n")
        arr?.forEach {
            report.append("    ")
            report.append(it.toString())
            report.append(SINGLE_LINE_SEP)
        }
        report.append(lineSeperator)
        // If the exception was thrown in a background thread inside
        // AsyncTask, then the actual exception can be found with getCause
        report.append("--------- Cause ---------\n\n")
        val cause = e?.cause
        if (cause != null) {
            report.append(cause.toString())
            report.append(DOUBLE_LINE_SEP)
            arr = cause.stackTrace
            for (i in arr.indices) {
                report.append("    ")
                report.append(arr[i].toString())
                report.append(SINGLE_LINE_SEP)
            }
        }
        // Getting the Device brand,model and sdk verion details.
        report.append(lineSeperator)
        report.append("--------- Device ---------\n\n")
        report.append("Brand: ")
        report.append(Build.BRAND)
        report.append(SINGLE_LINE_SEP)
        report.append("Device: ")
        report.append(Build.DEVICE)
        report.append(SINGLE_LINE_SEP)
        report.append("Model: ")
        report.append(Build.MODEL)
        report.append(SINGLE_LINE_SEP)
        report.append("Id: ")
        report.append(Build.ID)
        report.append(SINGLE_LINE_SEP)
        report.append("Product: ")
        report.append(Build.PRODUCT)
        report.append(SINGLE_LINE_SEP)
        report.append(lineSeperator)
        report.append("--------- Firmware ---------\n\n")
        report.append("SDK: ")
        report.append(Build.VERSION.SDK)
        report.append(SINGLE_LINE_SEP)
        report.append("Release: ")
        report.append(Build.VERSION.RELEASE)
        report.append(SINGLE_LINE_SEP)
        report.append("Incremental: ")
        report.append(Build.VERSION.INCREMENTAL)
        report.append(SINGLE_LINE_SEP)
        report.append(lineSeperator)

        Log.d("Report ::", report.toString())

        return report.toString()
    }


    override fun onActivityPaused(activity: Activity?) {

    }

    override fun onActivityResumed(activity: Activity?) {
        if(config.startActivity == null) {
            config.startActivity = activity
            hasActivity = false
        }
    }

    override fun onActivityStarted(activity: Activity?) {

    }

    override fun onActivityDestroyed(activity: Activity?) {
        if(!hasActivity){
            config.startActivity = null
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

    }

    override fun onActivityStopped(activity: Activity?) {

    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {

    }

}

private class ExceptionHandler( private var app: Application, private var handler: Thread.UncaughtExceptionHandler?):
    Thread.UncaughtExceptionHandler, Application.ActivityLifecycleCallbacks {

    private var activity: Activity? = null

    override fun onActivityPaused(activity: Activity?) {

    }

    override fun onActivityResumed(activity: Activity?) {
        this.activity = activity
    }

    override fun onActivityStarted(activity: Activity?) {

    }

    override fun onActivityDestroyed(activity: Activity?) {
        this.activity = null
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

    }

    override fun onActivityStopped(activity: Activity?) {

    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {

    }

    override fun uncaughtException(t: Thread?, e: Throwable?) {
        val data = extractCrashData(t,e)
        if(activity != null){
            val intent = Intent(app.applicationContext,activity?.javaClass)
            intent.putExtra(CRASH_DATA,data)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            app.startActivity(intent)

            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(10)

        }else{
            println("activity is null")
            handler?.uncaughtException(t,e)

        }
    }

    private fun extractCrashData(t: Thread?, e: Throwable?): String {
        var arr = e?.stackTrace
        val DOUBLE_LINE_SEP = "\n\n"
        val SINGLE_LINE_SEP = "\n"
        val report = StringBuilder(e.toString())
        val lineSeperator = "-------------------------------\n\n"
        report.append(DOUBLE_LINE_SEP)
        report.append("--------- Stack trace ---------\n\n")
        arr?.forEach {
            report.append("    ")
            report.append(it.toString())
            report.append(SINGLE_LINE_SEP)
        }
        report.append(lineSeperator)
        // If the exception was thrown in a background thread inside
        // AsyncTask, then the actual exception can be found with getCause
        report.append("--------- Cause ---------\n\n")
        val cause = e?.cause
        if (cause != null) {
            report.append(cause.toString())
            report.append(DOUBLE_LINE_SEP)
            arr = cause.stackTrace
            for (i in arr.indices) {
                report.append("    ")
                report.append(arr[i].toString())
                report.append(SINGLE_LINE_SEP)
            }
        }
        // Getting the Device brand,model and sdk verion details.
        report.append(lineSeperator)
        report.append("--------- Device ---------\n\n")
        report.append("Brand: ")
        report.append(Build.BRAND)
        report.append(SINGLE_LINE_SEP)
        report.append("Device: ")
        report.append(Build.DEVICE)
        report.append(SINGLE_LINE_SEP)
        report.append("Model: ")
        report.append(Build.MODEL)
        report.append(SINGLE_LINE_SEP)
        report.append("Id: ")
        report.append(Build.ID)
        report.append(SINGLE_LINE_SEP)
        report.append("Product: ")
        report.append(Build.PRODUCT)
        report.append(SINGLE_LINE_SEP)
        report.append(lineSeperator)
        report.append("--------- Firmware ---------\n\n")
        report.append("SDK: ")
        report.append(Build.VERSION.SDK)
        report.append(SINGLE_LINE_SEP)
        report.append("Release: ")
        report.append(Build.VERSION.RELEASE)
        report.append(SINGLE_LINE_SEP)
        report.append("Incremental: ")
        report.append(Build.VERSION.INCREMENTAL)
        report.append(SINGLE_LINE_SEP)
        report.append(lineSeperator)

        Log.d("Report ::", report.toString())

        return report.toString()
    }


}