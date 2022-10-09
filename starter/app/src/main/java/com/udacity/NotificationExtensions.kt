package com.udacity

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.NotificationCompat

const val NOTIFICATION_ID: Int = 0

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
//    val contentPendingIntent =        openActivityPendingIntent(applicationContext, MainActivity::class.java)
    val actionButtonPendingIntent =
        openActivityPendingIntent(applicationContext, DetailActivity::class.java)


    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.notification_channel_id)
    ).setSmallIcon(R.drawable.ic_assistant_black_24dp)
        .setContentTitle(
            applicationContext
                .getString(R.string.notification_title)
        )
        .setContentIntent(actionButtonPendingIntent)
        .setContentText(messageBody)
        .setAutoCancel(true)
        .setOnlyAlertOnce(true)
        .addAction(
            R.drawable.ic_launcher_foreground,
            applicationContext.getString(R.string.check_the_status),
            actionButtonPendingIntent
        )

    notify(NOTIFICATION_ID, builder.build())

}

private fun openActivityPendingIntent(
    applicationContext: Context, activity: Class<*>?
): PendingIntent? {

    val intent = Intent(applicationContext, activity)
    val bundle = Bundle()

   bundle.putString(DetailActivity.FILE_NAME, "TESTE")
  bundle.putInt(DetailActivity.FILE_STATUS, R.string.fail)
intent.putExtras(bundle)

    return PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}