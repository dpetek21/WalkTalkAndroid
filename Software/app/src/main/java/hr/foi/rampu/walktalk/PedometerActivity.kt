package hr.foi.rampu.walktalk

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import hr.foi.rampu.walktalk.database.PedometerDAO
import hr.foi.rampu.walktalk.navigation.NavigationSetup
import hr.foi.rampu.walktalk.services.PedometerService

private lateinit var sensorManager: SensorManager
private lateinit var pedometerDAO: PedometerDAO

class PedometerActivity : AppCompatActivity(), SensorEventListener {

    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f
    val ACTIVITY_RECOGNITION_REQUEST_CODE = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedometer)

        NavigationSetup.SetupNavigationDrawer(this)

        pedometerDAO = PedometerDAO()

        if (isPermissionGranted()) {
            requestPermission()
        }

        resetSteps()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val serviceIntent = Intent(applicationContext, PedometerService::class.java)
        serviceIntent.action = PedometerService.Actions.START.toString()

        startService(serviceIntent)

    }

    override fun onResume() {
        super.onResume()
        running = true

        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null) {
            Toast.makeText(this, "No sensor detected on this device", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Intent(applicationContext, PedometerService::class.java).also{
            it.action = PedometerService.Actions.STOP.toString()
        }
    }

    fun resetSteps() {
        var tv_stepsTaken = findViewById<TextView>(R.id.tv_stepsTaken)
        tv_stepsTaken.setOnClickListener {
            // This will give a toast message if the user want to reset the steps
            Toast.makeText(this, "Long tap to reset steps", Toast.LENGTH_SHORT).show()
        }

        tv_stepsTaken.setOnLongClickListener {

            pedometerDAO.savePedometerData(tv_stepsTaken.text.toString().toInt())

            previousTotalSteps = totalSteps
            tv_stepsTaken.text = 0.toString()

            true
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        var tv_stepsTaken = findViewById<TextView>(R.id.tv_stepsTaken)
        if (running) {
            totalSteps = event!!.values[0]
            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
            tv_stepsTaken.text = ("$currentSteps")
        }
    }


    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACTIVITY_RECOGNITION
        ) != PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                ACTIVITY_RECOGNITION_REQUEST_CODE
            )
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}