package dev.arinzedroid

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dev.arinzedroid.simplecrashhandler.R
import java.lang.Exception

internal class NotificationUtil(private val context: Context) {

    private val chanelId = "channelID"; private val notifyId = 21

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getAppName()
            val descriptionText = "This channel is used to send notifications about $name app"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(chanelId, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }else{

        }
    }

    fun createNotification(text: String ){

        createNotificationChannel()

        val mBuilder = NotificationCompat.Builder(context, chanelId)
            .setSmallIcon(getDrawable())
            .setLargeIcon(BitmapFactory.decodeResource(context.resources,getDrawable()))
            .setContentTitle(getAppName())
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(text))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)){
            notify(notifyId,mBuilder.build())
        }
    }

    private fun getAppName(): String{
        val appInfo = context.applicationInfo
        val stringId = appInfo.labelRes
        return if(stringId == 0){
            appInfo.nonLocalizedLabel.toString()
        }else{
            context.getString(stringId)
        }
    }

    private fun getDrawable(): Int{
        var ico = R.drawable.ic_warning
        try{
            ico = context.applicationInfo.icon
        }catch (ex: Exception){
            ex.printStackTrace()
        }
        return ico
    }
}