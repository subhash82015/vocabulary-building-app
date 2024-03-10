package com.app.vocabulary.firebase

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.app.vocabulary.R
import com.app.vocabulary.ui.activity.DashboardActivity
import com.cubeJeKxUser.firebase.MyWorker
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {


    companion object {
        private const val TAG = "MyFirebaseMessagingService "
    }

    @SuppressLint("LongLogTag")
    override fun onNewToken(p0: String) {
        Log.d(TAG, "onNewToken " + p0.toString())
        //sharedPreference = SharedPreferenceUtil.getInstance(this)
        //sharedPreference.deviceToken = p0
    }

    @SuppressLint("LongLogTag")
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "onMessageReceived" + remoteMessage.toString())

        if (remoteMessage.notification != null) {
            Log.d(TAG, "onMessageReceived" + remoteMessage.notification.toString())


            /* val notificationData = remoteMessage.notification
             val notificationType: String? = notificationData["notification_type"]
             val message: String? = notificationData["message"]
             val title: String? = notificationData["title"]
             if (message != null && notificationType != null && title != null) {
                 sendNotificationToTrackDriver(message,notificationType,title)
             }*/
        }
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            val notificationData = remoteMessage.data
            val notificationType: String? = notificationData["notification_type"]
            val message: String? = notificationData["message"]
            val title: String? = notificationData["title"]
            if (message != null && notificationType != null && title != null) {
                //  sendNotification(message, notificationType, title)
            }
        }

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                scheduleJob()
            } else {
                // Handle message within 10 seconds
                handleNow()
            }
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            sendNotification(it.body + "", "", it.title + "")

        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun sendNotification(
        message: String,
        notification_type: String,
        title: String
    ) {
        val intent = Intent(this, DashboardActivity::class.java)
//            .putExtra(IntentConstant.IS_FROM, IntentConstant.FIREBASE_NOTIFICATION)
//            .putExtra(IntentConstant.ORDER_ID, orderId)
       // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        val pendingIntent = PendingIntent.getActivity(
            this, 1, intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val mNotification = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = System.currentTimeMillis().toInt()
        val notificationBuilder = NotificationCompat.Builder(this, channelId.toString())
        notificationBuilder
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.logo)
            // .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setColor(ContextCompat.getColor(applicationContext, R.color.purple_200))
            .setSound(defaultSoundUri)
            .setStyle(NotificationCompat.BigTextStyle())
            .setContentIntent(pendingIntent).priority =
            Notification.PRIORITY_MAX

        val mChannel: NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val imp = NotificationManager.IMPORTANCE_HIGH
            mChannel = NotificationChannel(
                channelId.toString(), getString(R.string.app_name), imp
            )
            mChannel.description = title
            mChannel.importance = NotificationManager.IMPORTANCE_HIGH
            mChannel.lightColor = Color.CYAN
            mChannel.canShowBadge()
            mChannel.setShowBadge(true)
            mNotification.createNotificationChannel(mChannel)
        }
        mNotification.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }


    private fun scheduleJob() {
        // [START dispatch_job]
        val work = OneTimeWorkRequest.Builder(MyWorker::class.java).build()
        WorkManager.getInstance(this).beginWith(work).enqueue()
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    @SuppressLint("LongLogTag")
    private fun handleNow() {
        Log.d(TAG, "Short lived task is done.")
    }


}