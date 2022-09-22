package com.udacity

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

const val NOTIFICATION_ID: Int = 0

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.notification_channel_id)
    )  .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(applicationContext
            .getString(R.string.notification_title))
        .setContentText(messageBody)


    notify(NOTIFICATION_ID, builder.build())
}