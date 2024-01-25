package hr.foi.rampu.walktalk.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import hr.foi.rampu.walktalk.R
import hr.foi.rampu.walktalk.database.PedometerDAO

class PedometerService: Service(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var pedometerDAO: PedometerDAO
    private var totalSteps = 0
    private var initialStepCount = 0
    private var initialStepsRecorded = false
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

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        pedometerDAO = PedometerDAO()

        if (stepSensor == null) {
            stopSelf()
        } else {
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_GAME)
        }


    }

    override fun onDestroy() {
        super.onDestroy()

        pedometerDAO.savePedometerData(totalSteps)

        val serviceIntent = Intent(applicationContext, PedometerService::class.java)
        serviceIntent.action = PedometerService.Actions.STOP.toString()
        stopService(serviceIntent)
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
            .setContentText("Number of steps: $totalSteps")
            .build()
        startForeground(1, notification)
    }

    private fun updateNotification() {
        val notification = NotificationCompat.Builder(this, "pedometer_channel")
            .setSmallIcon(R.drawable.ic_attending_event)
            .setContentTitle("Pedometer service")
            .setContentText("Number of steps: $totalSteps")
            .build()
        startForeground(1, notification)
    }


    enum class Actions{
        START, STOP
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
            if(!initialStepsRecorded){
                initialStepCount = event.values[0].toInt()
                initialStepsRecorded = true
            }
            totalSteps = event.values[0].toInt() - initialStepCount
            updateNotification()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

}