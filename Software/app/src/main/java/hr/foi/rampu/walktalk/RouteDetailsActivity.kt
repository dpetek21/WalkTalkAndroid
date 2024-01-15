package hr.foi.rampu.walktalk

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
//import com.google.firebase.firestore.GeoPoint
import hr.foi.rampu.walktalk.entities.Route
import hr.foi.rampu.walktalk.firebaseHandler.RouteHandler
import hr.foi.rampu.walktalk.firebaseHandler.UserDataContainer
import hr.foi.rampu.walktalk.fragments.MapFragment
import hr.foi.rampu.walktalk.navigation.NavigationSetup
import org.osmdroid.util.GeoPoint

class RouteDetailsActivity : AppCompatActivity() {
    private lateinit var startPoint: GeoPoint
    private lateinit var endPoint: GeoPoint
    private var routeID:String?=null
    private var routeOwner:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_details)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, MapFragment())
            .commit()
        NavigationSetup.SetupNavigationDrawer(this)
        routeID = intent.getStringExtra("routeID")
        val routeName = intent.getStringExtra("routeName")
        routeOwner = intent.getStringExtra("routeOwner")
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
        if(routeName!="ADD NEW ROUTE")
            nameTextBox.setText(routeName)

        when (routeRating) {
            1 -> radioGroup.check(R.id.rbRating1)
            2 -> radioGroup.check(R.id.rbRating2)
            3 -> radioGroup.check(R.id.rbRating3)
        }

        val setStart : Button = findViewById(R.id.btnSetStart)
        setStart.setOnClickListener{
            val intent = Intent(this, LocationSelectorActivity::class.java)
            intent.putExtra("markerLatitude", startPoint.latitude)
            intent.putExtra("markerLongitude", startPoint.longitude)
            startActivityForResult(intent, 1)

        }
        val setEnd : Button = findViewById(R.id.btnSetEnd)
        setEnd.setOnClickListener{
            val intent = Intent(this, LocationSelectorActivity::class.java)
            intent.putExtra("markerLatitude", endPoint.latitude)
            intent.putExtra("markerLongitude", endPoint.longitude)
            startActivityForResult(intent, 2)
        }
        val save : Button = findViewById(R.id.btnSave)
        save.setOnClickListener{
            saveRoute()
        }
        val delete : Button = findViewById(R.id.btnDelete)
        delete.setOnClickListener{
            val handler = RouteHandler()
            var routeidlocal : String=""
            if(routeID!=null)
                routeidlocal= routeID as String
            if(routeidlocal!=""){
                handler.deleteRoute(routeidlocal)
                Toast.makeText(this, "Route deleted!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, RoutesActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun saveRoute() {
        val nameTextBox = findViewById<EditText>(R.id.nameTextBox)
        val name=nameTextBox.text.toString()
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val selectedButtonId = radioGroup.checkedRadioButtonId
        val rating : Int
        if (selectedButtonId != -1) {
            val selectedRadioButton: RadioButton = findViewById(selectedButtonId)
            rating = selectedRadioButton.tag.toString().toInt()
            if(name!=""){
                if(!isNullPoint(startPoint)){
                    if(!isNullPoint(endPoint)){
                        val route = Route(routeID,name,startPoint,endPoint,rating,UserDataContainer.username)
                        val handler = RouteHandler()
                        handler.updateRoute(route)
                        Toast.makeText(this, "Route saved!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, RoutesActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this, "Please set the ending point", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "Please set the starting point", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(this, "The route name textbox must not be empty!", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(this, "Please select a route difficulty rating!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        setMarkers()
    }
    fun setMarkers(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as MapFragment
        mapFragment.clearMarkers()
        if(!isNullPoint(startPoint))
            mapFragment.addMarker(startPoint, "Start Point")
        if(!isNullPoint(endPoint))
            mapFragment.addMarker(endPoint, "End Point")

        if(!isNullPoint(startPoint)&&!isNullPoint(endPoint)){
            mapFragment.zoomToPoints(startPoint,endPoint)
        }else if(!isNullPoint(startPoint)){
            mapFragment.zoomToPoints(startPoint,startPoint)
        }else if(!isNullPoint(endPoint)){
            mapFragment.zoomToPoints(endPoint,endPoint)
        }else{
            mapFragment.zoomToWorldMap();
        }

    }
    fun isNullPoint( point:GeoPoint): Boolean {
        if(point.longitude==0.0&&point.latitude==0.0){
            return true
        }else return false;
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val NewLatitude = data?.getDoubleExtra("Lattitude",0.0)
            val NewLongitude = data?.getDoubleExtra("Longitude",0.0)
            Toast.makeText(this, "Startpoint changed to: Lattitude: "+NewLatitude+" Longitude: "+NewLongitude, Toast.LENGTH_SHORT).show()
            if(NewLongitude!=null&&NewLatitude!=null)
                startPoint =  GeoPoint(NewLatitude,NewLongitude)
        }
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            val NewLatitude = data?.getDoubleExtra("Lattitude",0.0)
            val NewLongitude = data?.getDoubleExtra("Longitude",0.0)
            Toast.makeText(this, "Endpoint changed to: Lattitude: "+NewLatitude+" Longitude: "+NewLongitude, Toast.LENGTH_SHORT).show()
            if(NewLongitude!=null&&NewLatitude!=null)
                endPoint =  GeoPoint(NewLatitude,NewLongitude)
        }
        setMarkers()
    }
}