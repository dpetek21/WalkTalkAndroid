package hr.foi.rampu.walktalk.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.walktalk.R
import hr.foi.rampu.walktalk.entities.Friend

class PendingFriendsAdapter(val friendsList : MutableList<Friend>) : RecyclerView.Adapter<PendingFriendsAdapter.PendingFriendsViewHolder>() {
    inner class PendingFriendsViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
         val username : TextView
         val btnAccept : Button
         val btnDecline : Button
        init {
            username = view.findViewById(R.id.txtv_username_pending_friend)
            btnAccept = view.findViewById(R.id.btn_accept)
            btnDecline = view.findViewById(R.id.btn_decline)
        }

        fun bind(friend: Friend)
        {
            username.text = friend.username
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingFriendsViewHolder {
        val friendsView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.pending_friend_ist_item, parent, false)
        return PendingFriendsViewHolder(friendsView)
    }

    override fun getItemCount(): Int {
        return friendsList.size
    }

    override fun onBindViewHolder(holder: PendingFriendsViewHolder, position: Int) {
        holder.bind(friendsList[position])
        holder.btnAccept.setOnClickListener {

        }

        holder.btnDecline.setOnClickListener {
            friendsList.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}