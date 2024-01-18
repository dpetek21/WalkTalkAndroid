package hr.foi.rampu.walktalk.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import hr.foi.rampu.walktalk.R

class PedometerService: Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "pedometer_channel",
                "Pedometer Service",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            getSystemService(NotificationManager::class.java)?.createNotificationChannel(channel)
        }
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(){
        val notification = NotificationCompat.Builder(this, "pedometer_channel")
            .setSmallIcon(R.drawable.ic_attending_event)
            .setContentTitle("Pedometer service")
            .setContentText("Number of steps: 20")
            .build()
        startForeground(1, notification)
    }

    enum class Actions{
        START, STOP
    }

}