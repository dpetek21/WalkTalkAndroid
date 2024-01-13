package hr.foi.rampu.walktalk.firebaseHandler

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import hr.foi.rampu.walktalk.entities.Route
import org.osmdroid.util.GeoPoint

class RouteHandler {
    lateinit var owner : String
    lateinit var db : FirebaseFirestore
     init {
         owner=UserDataContainer.username
         db = FirebaseFirestore.getInstance()
    }
    fun addRoute(route: Route) {
        db.collection("routes")
            .add(route)
            .addOnSuccessListener { documentReference ->
                val autoGeneratedId = documentReference.id
                Log.d("Firestore", "Route saved with ID: $autoGeneratedId")

                val updatedRoute = route.copy(id = autoGeneratedId)
                updateRoute(updatedRoute)
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error adding route", e)
            }
    }
    fun updateRoute(route : Route){
        val routeId = route.id
        if (routeId != null) {
            db.collection("routes")
                .document(routeId)
                .set(route)
                .addOnSuccessListener {
                    Log.d("Firestore", "Route updated with ID: $routeId")
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error updating route", e)
                }
        } else {
            addRoute(route);
        }
    }
    fun getRoutesOfOwner(owner: String, callback: (List<Route>) -> Unit) {
        db.collection("routes")
            .whereEqualTo("owner", owner)
            .get()
            .addOnSuccessListener { result ->
                val routesList = mutableListOf<Route>()
                routesList.add(Route(null,"The user's routes:", GeoPoint(0.0, 0.0), GeoPoint(0.0, 0.0), -1, ""))
                for (document in result) {
                    val id = document.id
                    val name = document.getString("name") ?: ""
                    val startLatitude = document.getDouble("start.latitude") ?: 0.0
                    val startLongitude = document.getDouble("start.longitude") ?: 0.0
                    val endLatitude = document.getDouble("end.latitude") ?: 0.0
                    val endLongitude = document.getDouble("end.longitude") ?: 0.0
                    val rating = document.getLong("rating")?.toInt() ?: -1
                    val owner = document.getString("owner") ?: ""
                    val route = Route(id, name, GeoPoint(startLatitude, startLongitude), GeoPoint(endLatitude, endLongitude), rating, owner)
                    routesList.add(route)
                }
                routesList.add(Route(null,"ADD NEW ROUTE", GeoPoint(0.0, 0.0), GeoPoint(0.0, 0.0), -1, ""))
                callback(routesList)
            }
            .addOnFailureListener { exception ->
                callback(emptyList())
            }
    }
    fun deleteRoute(routeId: String) {
        db.collection("routes")
            .document(routeId)
            .delete()
            .addOnSuccessListener {
                Log.d("Firestore", "Route deleted with ID: $routeId")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error deleting route", e)
            }
    }
}