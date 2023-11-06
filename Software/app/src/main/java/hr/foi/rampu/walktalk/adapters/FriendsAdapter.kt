package hr.foi.rampu.walktalk.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.walktalk.R
import hr.foi.rampu.walktalk.entities.Friend

class FriendsAdapter(val friendsList : List<Friend>) : RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>() {
    inner class FriendsViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val friendsView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.friend_list_item, parent, false)
        return FriendsViewHolder(friendsView)
    }

    override fun getItemCount(): Int {
        return friendsList.size
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}