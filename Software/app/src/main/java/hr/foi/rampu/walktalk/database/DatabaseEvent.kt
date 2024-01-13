package hr.foi.rampu.walktalk.database

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import hr.foi.rampu.walktalk.entities.Event
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Locale

object DatabaseEvent {
    var event : Event? = null

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
        var id : String? = null
        try {
            database
                .collection("events")
                .whereEqualTo("name", event!!.name)
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
            database.collection("events").document(id!!).update("invites", DatabaseFriend.username).await()
             true
        }

        catch (error : Exception) {
            Log.e("FIREBASE_ERROR", error.message!!)
             false
        }

    }
}