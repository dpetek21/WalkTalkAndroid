package hr.foi.rampu.walktalk.entities

import java.util.Date

data class Event(
    var name : String = "",
    val numberOfKilometers : Double = 0.0,
    var pace: String = "",
    var date:Date? = null,
    val organizer: String = "",
    val route: Route? = null,
    val isPublic : Boolean = true,
    var pendingInvites : ArrayList<String>? = null,
    var acceptedInvites : ArrayList<String>? = null
) {
}