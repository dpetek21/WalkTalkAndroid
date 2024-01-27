package hr.foi.rampu.walktalk.database

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import hr.foi.rampu.walktalk.entities.LeaderboardItem
import hr.foi.rampu.walktalk.firebaseHandler.UserDataContainer
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import java.util.Date

class LeaderboardDAO {
    private val database = FirebaseFirestore.getInstance()
    private val loggedInUser = UserDataContainer.username

    suspend fun getDayStepsLogs(): List<LeaderboardItem>{
        val usersCollection = database.collection("users")
        val userDocument = usersCollection.document(loggedInUser)
        Log.i("User document", userDocument.id)

        val friendsReferences = userDocument.get().await().get("friends") as List<DocumentReference>
        Log.i("FriendsReference", friendsReferences[0].id)

        val leaderboardItems = mutableListOf<LeaderboardItem>()
        val currentDate = Date().time

        friendsReferences.forEach { friendReference ->
            Log.i("Ušli", friendReference.id)
            val stepsLogCollection = friendReference.collection("steps_logs")
            Log.i("FriendsReference2", stepsLogCollection.id)
            val stepsLogDocuments = stepsLogCollection.get().await()
            var steps = 0
            if(!stepsLogDocuments.isEmpty){
                stepsLogDocuments.documents.forEach { stepsLogDocument ->
                    Log.i("StepsLogDocuments", stepsLogDocument.id)
                    val timestamp = stepsLogDocument.getTimestamp("timestamp")?.toDate()?.time ?: 0
                    if (isSameDay(currentDate, timestamp)) {
                        steps += stepsLogDocument.getLong("steps")?.toInt() ?: 0
                    }
                }
            }
            val leaderboardItem = LeaderboardItem(friendReference.id, steps)
            leaderboardItems.add(leaderboardItem)
        }
        return leaderboardItems
    }

    private fun isSameDay(timestamp1: Long, timestamp2: Long): Boolean {
        val cal1 = Calendar.getInstance()
        cal1.timeInMillis = timestamp1

        val cal2 = Calendar.getInstance()
        cal2.timeInMillis = timestamp2

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

}