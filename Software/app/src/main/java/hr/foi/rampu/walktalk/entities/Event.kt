package hr.foi.rampu.walktalk.entities

import com.google.firebase.firestore.GeoPoint
import java.util.Date

data class Event(
    var name : String = "",
    val numberOfKilometers : Double = 0.0,
    val numberOfPeople : Int = 0,
    var pace: String = "",
    var date:Date? = null,
    val organizer: String = "",
    val route: List<GeoPoint>? = null,
    val isPublic : Boolean = true,
    val invites : ArrayList<String>? = null
) {
}