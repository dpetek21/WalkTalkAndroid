package hr.foi.rampu.walktalk.klase_za_chat

class Message {
    var message: String? = null
    var sender: String? = null

    constructor(message: String?, sender: String?){
        this.message = message
        this.sender = sender
    }
}