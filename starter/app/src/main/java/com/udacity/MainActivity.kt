package com.udacity

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0
    private lateinit var loadingButton: LoadingButton
    private lateinit var radioGroup: RadioGroup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        radioGroup = findViewById(R.id.repository_radio_group)
        loadingButton = findViewById(R.id.loading_button)

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        createChannel(CHANNEL_ID, "DOWNLOADS")

        loading_button.setOnClickListener {
            if (isSomeRepositoryOptionChecked()) {
                downloadSelectedRepository()
            }
        }

        if (!isSomeRepositoryOptionChecked()) {
            askUserToSelectARepository()
        }
    }

    private fun downloadSelectedRepository() {
        when (radioGroup.checkedRadioButtonId) {
            R.id.radio_glide -> download(R.string.glide_radio_file_name, GLIDE)
            R.id.radio_load_app -> download(R.string.load_app_file_name, LOAD_APP)
            R.id.radio_retrofit -> download(R.string.retrofit_radio_file_name, RETROFIT)
        }
    }

    private fun askUserToSelectARepository() {
        loadingButton.state = ButtonState.Loading
        Toast.makeText(this, getString(R.string.select_the_file_file_name), Toast.LENGTH_LONG)
            .show()
    }

    private fun isSomeRepositoryOptionChecked() = radioGroup.checkedRadioButtonId != -1

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

        }
    }

    private fun sendNotification(cursor: Cursor) {
        val notificationManager = ContextCompat.getSystemService(
            application,
            NotificationManager::class.java
        ) as NotificationManager


        notificationManager.sendNotification(
            getString(R.string.notification_description),
            application, cursor
        )
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            loadingButton.state = ButtonState.Completed
            if (downloadID == id) {
                if (intent.action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                    val query = DownloadManager.Query()
                    query.setFilterById(intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0));
                    val manager =
                        context!!.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                    val cursor: Cursor = manager.query(query)
                    if (cursor.moveToFirst()) {
                        if (cursor.count > 0) {
                            sendNotification(
                                cursor
                            )
                        }
                    }
                }
            }
        }
    }


    private fun download(titleTextId: Int, url: String) {
        loadingButton.state = ButtonState.Clicked

        val request =
            DownloadManager.Request(Uri.parse(url))
                .setTitle(getString(titleTextId))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request)
        // enqueue puts the download request in the queue.
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


