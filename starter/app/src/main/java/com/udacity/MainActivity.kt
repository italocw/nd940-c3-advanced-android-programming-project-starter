package com.udacity

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0
    private lateinit var loadingButton: LoadingButton
    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action
    private lateinit var radioGroup: RadioGroup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        createChannel(CHANNEL_ID, "NOTIFICAÇÃO")

        radioGroup = findViewById(R.id.repository_radio_group)

        loading_button.setOnClickListener {
            when (radioGroup.checkedRadioButtonId) {
                R.id.radio_glide -> download(GLIDE)
                R.id.radio_load_app -> download(LOAD_APP)
                R.id.radio_retrofit -> download(RETROFIT)
            }
        }

        loadingButton = findViewById(R.id.loading_button)
    }

    override fun onResume() {
        super.onResume()
        if (!radioGroup.isSelected) {
            loadingButton.state = ButtonState.Loading
            Toast.makeText(this, getString(R.string.select_the_file_file_name), Toast.LENGTH_LONG).show()
        }
    }

    private fun createChannel(channelId: String, channelName: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW
            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)

            val notificationManager = getSystemService(
                NotificationManager::class.java
            )

            notificationManager.createNotificationChannel(notificationChannel)
            //notificationManager.cancelNotifications()

        }
    }

    private fun sendNotification() {
        val notificationManager = ContextCompat.getSystemService(
            application,
            NotificationManager::class.java
        ) as NotificationManager


        notificationManager.sendNotification(
            getString(R.string.notification_description),
            application
        )
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            loadingButton.state = ButtonState.Completed
            sendNotification()

        }
    }

    private fun download(url: String) {
        animateButton()
        val request =
            DownloadManager.Request(Uri.parse(url))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request)
        // enqueue puts the download request in the queue.
    }

    private fun animateButton() {
        loadingButton.state = ButtonState.Loading
    }


    companion object {
        private const val GLIDE =
            "https://github.com/bumptech/glide/archive/master.zip"
        private const val LOAD_APP =
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
        private const val RETROFIT =
            "https://github.com/square/retrofit/master.zip"

        private const val CHANNEL_ID = "channelId"
    }

}


