package hr.foi.rampu.walktalk.entities

import com.google.firebase.firestore.GeoPoint
import java.util.Date

data class Event(
    val name : String,
    val numberOfKilometers : Double,
    val numberOfPeople : Int,
    val pace: String,
    val date: Date,
    val organizer: String,
    val route: List<GeoPoint>?,
    val isPublic : Boolean
)