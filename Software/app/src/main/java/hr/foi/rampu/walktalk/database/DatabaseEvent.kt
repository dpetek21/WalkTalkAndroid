package hr.foi.rampu.walktalk.database

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import hr.foi.rampu.walktalk.entities.Event
import hr.foi.rampu.walktalk.entities.Route
import hr.foi.rampu.walktalk.firebaseHandler.UserDataContainer
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import org.osmdroid.util.GeoPoint
import java.text.SimpleDateFormat
import java.util.Locale

object DatabaseEvent {
    var event : Event? = null

    private suspend fun getEventDocument() : String = coroutineScope {
        var id : String? = null
        val database = Firebase.firestore
        database
            .collection("events")
            .whereEqualTo("name", event!!.name)
            .whereEqualTo("organizer", event!!.organizer)
            .get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    throw Exception("Event not found in Firestore")
                }
                if (it.size() > 1) {
                    throw Exception("Found multiple events in Firestore")
                }
                id = it.documents[0].id
            }
            .await()
        id!!
    }

    suspend fun getPublicEvents(): List<Event> = coroutineScope {
        val eventList = mutableListOf<Event>()
        val database = Firebase.firestore
        database
            .collection("events")
            .whereEqualTo("isPublic", true)
            .get()
            .addOnSuccessListener { events ->
                events.forEach { event ->
                    val sdf = SimpleDateFormat("dd.mm.yyyy.", Locale.US)
                    val name = event.getString("name") ?: ""
                    val pace = event.getString("pace") ?: ""
                    val dateFirestore = event.getDate("date")!!
                    val parsedDate = sdf.format(dateFirestore)
                    val pendingInvites = event.get("pendingInvites") as ArrayList<String>
                    val acceptedInvites = event.get("acceptedInvites") as ArrayList<String>
                    val organizer = event.getString("organizer") ?: ""
                    val routeId = event.getString("route.id") ?: ""
                    val routeName = event.getString("route.name") ?: ""
                    val startLatitude = event.getDouble("route.start.latitude") ?: 0.0
                    val startLongitude = event.getDouble("route.start.longitude") ?: 0.0
                    val endLatitude = event.getDouble("route.end.latitude") ?: 0.0
                    val endLongitude = event.getDouble("route.end.longitude") ?: 0.0
                    val rating = event.getLong("route.rating")?.toInt() ?: -1
                    val owner = event.getString("route.owner") ?: ""
                    val routeAssignedToEvent = Route(routeId, routeName, GeoPoint(startLatitude, startLongitude), GeoPoint(endLatitude, endLongitude), rating, owner)

                    val newEvent = Event(
                        name,
                        0.0,
                        pace,
                        sdf.parse(parsedDate),
                        organizer,
                        routeAssignedToEvent,
                        true,
                        pendingInvites,
                        acceptedInvites
                        )
                    eventList.add(newEvent) //event.toObject<Event>())
                }
            }.await()
        eventList.toList()
    }

    fun addNewEvent(newEvent: Event) {
        val event = hashMapOf(
            "name" to newEvent.name,
            "numberOfKilometers" to newEvent.numberOfKilometers,
            "pace" to newEvent.pace,
            "date" to newEvent.date,
            "route" to newEvent.route,
            "organizer" to DatabaseFriend.username,
            "isPublic" to newEvent.isPublic,
            "pendingInvites" to newEvent.pendingInvites,
            "acceptedInvites" to newEvent.acceptedInvites
        )


        val database = Firebase.firestore
        database.collection("events").add(event).addOnSuccessListener {
            Log.i("EVENT_ADD_SUCCESS", "Event added successfully into firestore")
        }
            .addOnFailureListener {
                Log.i("EVENT_ADD_ERROR", it.toString())
            }

    }
    suspend fun sendInvite() : Boolean = coroutineScope {
        val database = Firebase.firestore
        try {
            val id = getEventDocument()
            database.collection("events").document(id).update("pendingInvites", FieldValue.arrayUnion(UserDataContainer.username)).await()
            if (event!!.pendingInvites != null) {
                event!!.pendingInvites?.add(UserDataContainer.username)
            }
            else {
                event!!.pendingInvites = arrayListOf(UserDataContainer.username)
            }
             true
        }

        catch (error : Exception) {
            Log.e("FIREBASE_ERROR", error.message.toString())
             false
        }

    }


    suspend fun cancelEvent(): Boolean = coroutineScope {
        try {
            val id = getEventDocument()
            val database = Firebase.firestore
            database.collection("events").document(id).delete().await()
            event = null
            true
        }
        catch (error : Exception)
        {
            Log.e("EVENT_DELETION_ERROR",error.message.toString())
            false
        }
    }

    fun checkIfUserSentInvite () : Boolean  {
        return try {
            event!!.pendingInvites?.contains(UserDataContainer.username) ?: false
        } catch(error: Exception) {
            Log.e("EVENT_CHECKING_ERROR",error.message.toString())
            false
        }
    }

    suspend fun updateEvent(eventToUpdate: Event): Boolean  = coroutineScope{

        try {
            val id = getEventDocument()
            val updatedEvent = mapOf(
                "name" to eventToUpdate.name,
                "numberOfKilometers" to eventToUpdate.numberOfKilometers,
                "pace" to eventToUpdate.pace,
                "date" to eventToUpdate.date,
                "route" to eventToUpdate.route,
                "isPublic" to eventToUpdate.isPublic
            )
            val database = Firebase.firestore
            database.collection("events").document(id).update(updatedEvent).await()
            event = eventToUpdate
            true
        }
        catch (error : Exception) {
            Log.e("FIRESTORE_UPDATE_EVENT_ERROR",error.message.toString())
            false
        }

    }

     suspend fun acceptInvite(usernameToAccept: String) = coroutineScope {
        try {
            val id = getEventDocument()
            val database = Firebase.firestore

            database.collection("events").document(id).update("pendingInvites",FieldValue.arrayRemove(usernameToAccept)).await()
            database.collection("events").document(id).update("acceptedInvites",FieldValue.arrayUnion(usernameToAccept)).await()

            event!!.pendingInvites!!.remove(usernameToAccept)

            if (event!!.acceptedInvites != null) {
                event!!.acceptedInvites!!.add(usernameToAccept)
            }
            else {
                event!!.acceptedInvites = arrayListOf(usernameToAccept)
            }

             true
        }

        catch (error : Exception) {
            Log.e("FIREBASE_ACCEPT_INVITE_ERROR",error.message.toString())
             false
        }

    }

    suspend fun declineInvite(usernameToDecline: String) = coroutineScope {
        try {
            val id = getEventDocument()
            val database = Firebase.firestore

            database.collection("events").document(id).update("pendingInvites",FieldValue.arrayRemove(usernameToDecline)).await()

            event!!.pendingInvites!!.remove(usernameToDecline)

            true
        }

        catch (error : Exception) {
            Log.e("FIREBASE_DECLINE_INVITE_ERROR",error.message.toString())
            false
        }
    }

    fun checkIfUserAccepted(): Boolean {
        return try {
            event!!.acceptedInvites?.contains(UserDataContainer.username) ?: false
        } catch(error: Exception) {
            Log.e("EVENT_CHECKING_ACCEPTED_INVITES_ERROR",error.message.toString())
            false
        }

    }
}