package hr.foi.rampu.walktalk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.walktalk.R
import hr.foi.rampu.walktalk.adapters.PendingFriendsAdapter
import hr.foi.rampu.walktalk.database.DatabaseFriend
import kotlinx.coroutines.launch


class PendingFriendsFragment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pending_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            recyclerView = view.findViewById(R.id.rv_pending_friends)
            getFriendRequests()
            recyclerView.layoutManager = LinearLayoutManager(view.context)
        }

    }

    private suspend fun getFriendRequests() {
        val friends = DatabaseFriend.getPendingFriendRequests()
        recyclerView.adapter = PendingFriendsAdapter(friends)
    }

}