package hr.foi.rampu.walktalk.entities

import com.google.firebase.firestore.GeoPoint
import java.util.Date

data class Event(
    val name : String = "",
    val numberOfKilometers : Double = 0.0,
    val numberOfPeople : Int = 0,
    val pace: String = "",
    val date:Date? = null,
    val organizer: String = "",
    val route: List<GeoPoint>? = null,
    val isPublic : Boolean = true,
    val invites : ArrayList<String>? = null
) {
}