package hr.foi.rampu.walktalk

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.RadioGroup
import hr.foi.rampu.walktalk.fragments.MapFragment
import hr.foi.rampu.walktalk.fragments.RoutesFragment
import hr.foi.rampu.walktalk.navigation.NavigationSetup
import org.osmdroid.util.GeoPoint

class RouteDetailsActivity : AppCompatActivity() {
    private lateinit var startPoint: GeoPoint
    private lateinit var endPoint: GeoPoint
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_details)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, MapFragment())
            .commit()
        NavigationSetup.SetupNavigationDrawer(this)
        val routeName = intent.getStringExtra("routeName")
        val routeOwner = intent.getStringExtra("routeOwner")
        val routeRating = intent.getIntExtra("routeRating", 1)
        val startLatitude = intent.getDoubleExtra("routeStartLatitude", 0.0)
        val startLongitude = intent.getDoubleExtra("routeStartLongitude", 0.0)
        val endLatitude = intent.getDoubleExtra("routeEndLatitude", 0.0)
        val endLongitude = intent.getDoubleExtra("routeEndLongitude", 0.0)
        Log.v(TAG, "StartLattitude je: "+startLatitude);
        Log.v(TAG, "StartLongitude je: "+startLongitude);
        Log.v(TAG, "endLatitude je: "+endLatitude);
        Log.v(TAG, "endLongitude je: "+endLongitude);
        startPoint = GeoPoint(startLatitude, startLongitude)
        endPoint = GeoPoint(endLatitude, endLongitude)

        val nameTextBox = findViewById<EditText>(R.id.nameTextBox)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        nameTextBox.setText(routeName)

        when (routeRating) {
            1 -> radioGroup.check(R.id.rbRating1)
            2 -> radioGroup.check(R.id.rbRating2)
            3 -> radioGroup.check(R.id.rbRating3)
        }
    }
    override fun onResume() {
        super.onResume()
        val mapFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as MapFragment
        mapFragment.addMarker(startPoint, "Start Point")
        mapFragment.addMarker(endPoint, "End Point")
    }
}