package com.bd.ordermanagementapp.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.bd.ordermanagementapp.R
import com.bd.ordermanagementapp.data.notification.NotificationDataProvider
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.android.ext.android.inject
import kotlin.random.Random

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val fcmTokenProvider: NotificationDataProvider by inject()

    companion object {
        private const val TAG = "MyFirebaseMsgService"
        private const val ORDER_CHANNEL_ID = "order_updates_channel"
        private const val ORDER_CHANNEL_NAME = "Order Updates"
        private const val ORDER_CHANNEL_DESCRIPTION = "Notifications about order status changes"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        remoteMessage.data.isNotEmpty().let {
            if (it) {
                println("$TAG: Message data payload: " + remoteMessage.data)
                val orderId = remoteMessage.data["orderId"]
                val title =
                    remoteMessage.data["title"] ?: getString(R.string.app_name) // Default title
                val body =
                    remoteMessage.data["body"] ?: "You have a new order update." // Default body

                if (!orderId.isNullOrBlank()) {
                    sendOrderNotification(title, body, orderId)
                } else {
                    println("$TAG: Received data message without orderId.")
                }
            } else {
                println("$TAG: Received message without data payload.")
            }
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        fcmTokenProvider.saveFCMToken(token = token)
    }

    /**
     * Create and show a notification containing the received FCM message,
     * configured to deep link to the order details screen.
     *
     * @param title Notification title.
     * @param messageBody Notification body.
     * @param orderId The ID of the order to deep link to.
     */
    private fun sendOrderNotification(title: String, messageBody: String, orderId: String) {
        val deepLinkUri = "orderapp://order.details/$orderId".toUri()
        val intent = Intent(Intent.ACTION_VIEW, deepLinkUri).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            `package` = applicationContext.packageName
        }

        // 2. Create the PendingIntent
        // Use a unique request code if you might have multiple pending intents active
        // Or use FLAG_UPDATE_CURRENT if you want subsequent notifications for the same order
        // to update the existing one's intent extras (if you add extras).
        val requestCode = Random.nextInt() // Simple unique request code
        val pendingIntentFlag = PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        val pendingIntent = PendingIntent.getActivity(
            this, requestCode, intent, pendingIntentFlag
        )

        // 3. Build the Notification
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, ORDER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_icon)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true) // Dismiss notification when tapped
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent) // Set the PendingIntent (triggers deep link)
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Or other priority
            .setColor(
                ContextCompat.getColor(
                    this,
                    R.color.notification_accent_color
                )
            ) // Optional: CHANGE: Use your color

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 4. Create Notification Channel (Required for Android Oreo and above)
        createNotificationChannel(notificationManager)

        // 5. Show the Notification
        // Use a unique ID for each notification, or reuse an ID to update existing ones
        val notificationId = orderId.hashCode() // Use orderId hash or another logic for ID
        notificationManager.notify(notificationId, notificationBuilder.build())
        println("$TAG: Notification sent for order $orderId")
    }


    private fun createNotificationChannel(notificationManager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                ORDER_CHANNEL_ID,
                ORDER_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = ORDER_CHANNEL_DESCRIPTION
                // Configure other channel settings (lights, vibration, etc.) if desired
                enableLights(true)
                lightColor = ContextCompat.getColor(
                    applicationContext,
                    R.color.notification_accent_color
                ) // Optional
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
            println("$TAG: Notification channel created.")
        }
    }

}