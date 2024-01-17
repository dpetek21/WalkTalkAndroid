package hr.foi.rampu.walktalk.database

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import hr.foi.rampu.walktalk.firebaseHandler.UserDataContainer

class PedometerDAO {
    private val database = FirebaseFirestore.getInstance()
    private val loggedInUser = UserDataContainer.username

    fun savePedometerData(steps: Int){
        val usersCollection = database.collection("users")
        val userDocument = usersCollection.document(loggedInUser)
        database.runTransaction { transaction ->
            val currentSteps = transaction.get(userDocument).getLong("steps_made") ?: 0
            val newSteps = currentSteps + steps
            transaction.set(userDocument, mapOf("steps_made" to newSteps), SetOptions.merge())
            newSteps
        }.addOnSuccessListener {
                Log.d("Pedometer", "Pedometer data saved successfully")
        }.addOnFailureListener { e ->
                Log.e("Pedometer", "Error saving pedometer data", e)
        }

    }
}