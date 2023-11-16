package hr.foi.rampu.walktalk.adapteri_za_chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import hr.foi.rampu.walktalk.R
import hr.foi.rampu.walktalk.klase_za_chat.Message

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>, val sender: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val itemReceived = 1;
    val itemSent = 2;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType == 1){

            val view: View = LayoutInflater.from(context).inflate(R.layout.receive_message, parent, false)
            return ReceiveMessageViewHolder(view)

        }else{

            val view: View = LayoutInflater.from(context).inflate(R.layout.sent_message, parent, false)
            return SentMessageViewHolder(view)

        }

    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessage = messageList[position]

        /*if(holder.javaClass == SentMessageViewHolder::class.java){
            //for sent view holder
            val viewHolder = holder as SentMessageViewHolder
            holder.sentMessage.text = currentMessage.message

        }else{
            //for receive view holder
            val viewHolder = holder as ReceiveMessageViewHolder
            holder.receivedMessage.text = currentMessage.message
        }*/

        if (holder is SentMessageViewHolder) {
            // For sent view holder
            holder.sentMessage.text = currentMessage.message
        } else if (holder is ReceiveMessageViewHolder) {
            // For receive view holder
            holder.receivedMessage.text = currentMessage.message
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (messageList[position].sender == sender) itemSent else itemReceived
    }


    class SentMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.findViewById<TextView>(R.id.txt_sent_massage)
    }

    class ReceiveMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val receivedMessage = itemView.findViewById<TextView>(R.id.txt_receive_massage)
    }

}