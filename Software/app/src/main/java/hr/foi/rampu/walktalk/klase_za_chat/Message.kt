package hr.foi.rampu.walktalk.klase_za_chat

import java.sql.Timestamp

class Message {
    var message: String? = null
    var sender: String? = null
    var receiver: String? = null
    var timestamp: com.google.firebase.Timestamp? = null

    constructor(message: String?, sender: String?, receiver: String?, timestamp: com.google.firebase.Timestamp?){
        this.message = message
        this.sender = sender
        this.receiver = receiver
        this.timestamp = timestamp
    }
}