package hr.foi.rampu.walktalk.entities

import com.google.firebase.Timestamp

data class StepsLog(
    val steps: Int,
    val timestamp: Timestamp
)