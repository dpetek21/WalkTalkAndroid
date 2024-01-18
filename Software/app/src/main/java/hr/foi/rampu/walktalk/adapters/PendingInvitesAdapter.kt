package hr.foi.rampu.walktalk.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.walktalk.R

class PendingInvitesAdapter(val invitesList: ArrayList<String>) : RecyclerView.Adapter<PendingInvitesAdapter.PendingInvitesViewHolder>() {
    inner class PendingInvitesViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val username : TextView
        val btnAccept : Button
        val btnDecline : Button

        init {
            username = view.findViewById(R.id.txtv_username_pending_friend)
            btnAccept = view.findViewById(R.id.btn_accept)
            btnDecline = view.findViewById(R.id.btn_decline)
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
        holder.bind(invitesList[position])

        holder.btnAccept.setOnClickListener {

        }

        holder.btnDecline.setOnClickListener {

        }
    }
}