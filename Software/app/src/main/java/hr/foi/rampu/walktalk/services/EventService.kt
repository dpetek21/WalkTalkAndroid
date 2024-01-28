package hr.foi.rampu.walktalk.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import hr.foi.rampu.walktalk.R


class EventService(val context: Context) : Service() {
    val id = "event_channel"
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

     private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                id,
                "Event Service",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendEventStartedNotification(context: Context,eventName: String) {
        createNotificationChannel()
        val builder = NotificationCompat
            .Builder(context,id)
            .setSmallIcon(R.drawable.ic_attending_event)
            .setContentTitle("Event started")
            .setContentText("You have started a new event called: $eventName")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify(10000,builder.build())
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }
}