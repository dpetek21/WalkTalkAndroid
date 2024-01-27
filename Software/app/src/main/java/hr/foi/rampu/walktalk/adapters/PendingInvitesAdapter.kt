package hr.foi.rampu.walktalk.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.walktalk.R
import hr.foi.rampu.walktalk.database.ChatsListDAO
import hr.foi.rampu.walktalk.database.DatabaseEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PendingInvitesAdapter(val invitesList: ArrayList<String>,  val coroutineScope: CoroutineScope, private val acceptCallback : suspend (String) -> Unit, private val declineCallback : suspend (String) -> Unit) : RecyclerView.Adapter<PendingInvitesAdapter.PendingInvitesViewHolder>() {
    inner class PendingInvitesViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val username : TextView
        val btnAccept : Button
        val btnDecline : Button
        val chatsListDAO : ChatsListDAO

            init {
                chatsListDAO = ChatsListDAO()
                username = view.findViewById(R.id.txtv_username_pending_invite)
                btnAccept = view.findViewById(R.id.btn_accept_invite)
                btnDecline = view.findViewById(R.id.btn_decline_invite)
            }

        fun bind(user: String)
        {
            username.text = user
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingInvitesAdapter.PendingInvitesViewHolder {
        val invitesView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.pending_invite_item, parent, false)
        return PendingInvitesViewHolder(invitesView)
    }

    override fun getItemCount(): Int {
        return invitesList.size
    }

    override fun onBindViewHolder(holder: PendingInvitesViewHolder, position: Int) {
        try {
            holder.bind(invitesList.getOrNull(holder.adapterPosition)!!)
            holder.btnAccept.setOnClickListener {
                coroutineScope.launch {
                    acceptCallback(invitesList[holder.adapterPosition])
                    notifyItemRemoved(holder.adapterPosition)
                }
                holder.chatsListDAO.addGroupChatToUsersChatCollection(holder.username.text.toString(), DatabaseEvent.event!!.name)
            }

            holder.btnDecline.setOnClickListener {
                coroutineScope.launch {
                    if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                        declineCallback(invitesList[holder.adapterPosition])
                        notifyItemRemoved(holder.adapterPosition)
                    }
                }
            }
        }
        catch (error : Exception) {
            Log.e("ADAPTER_ERR",error.message.toString())
        }

    }
}