package hr.foi.rampu.walktalk

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import hr.foi.rampu.walktalk.fragments.MapFragment
import hr.foi.rampu.walktalk.navigation.NavigationSetup
import org.osmdroid.util.GeoPoint

class LocationSelectorActivity : AppCompatActivity() {
    private lateinit var startPoint: GeoPoint
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_selector)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, MapFragment())
            .commit()
        NavigationSetup.SetupNavigationDrawer(this)
        val save : Button = findViewById(R.id.btnSaveLocation);
        save.setOnClickListener{
            val mapFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as MapFragment
            val resultIntent = Intent()
            resultIntent.putExtra("Lattitude", mapFragment.currentMarker!!.point.latitude)
            resultIntent.putExtra("Longitude", mapFragment.currentMarker!!.point.longitude)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
    override fun onResume() {
        super.onResume()
        val mapFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as MapFragment
        mapFragment.SettingPoint=1;
        val startLatitude = intent.getDoubleExtra("markerLatitude", 0.0)
        val startLongitude = intent.getDoubleExtra("markerLongitude", 0.0)
        startPoint = GeoPoint(startLatitude, startLongitude)
        mapFragment.handleTap(startPoint)
        if(startLongitude==0.0&&startLatitude==0.0){
            mapFragment.zoomToWorldMap();
        }else {
            mapFragment.zoomToPoints(startPoint, startPoint);
        }
    }
}