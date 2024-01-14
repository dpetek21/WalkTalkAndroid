package hr.foi.rampu.walktalk.database

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import hr.foi.rampu.walktalk.entities.Event
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
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
                    eventList.add(event.toObject<Event>())
                }
            }.await()
        eventList.toList()
    }

    fun addNewEvent(newEvent: Event) {
        val sdf = SimpleDateFormat("dd.MM.yyyy.", Locale.US)
        val event = hashMapOf(
            "name" to newEvent.name,
            "numberOfKilometers" to newEvent.numberOfKilometers,
            "numberOfPeople" to newEvent.numberOfPeople,
            "pace" to newEvent.pace,
            "date" to newEvent.date,
            "route" to newEvent.route,
            "organizer" to DatabaseFriend.username,
            "isPublic" to newEvent.isPublic
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
            database.collection("events").document(id).update("invites", FieldValue.arrayUnion(DatabaseFriend.username)).await()
            event!!.invites!!.add(DatabaseFriend.username)
             true
        }

        catch (error : Exception) {
            Log.e("FIREBASE_ERROR", error.message!!)
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
            event!!.invites!!.contains(DatabaseFriend.username)
        } catch(error: Exception) {
            Log.e("EVENT_CHECKING_ERROR",error.message.toString())
            false
        }
    }
}