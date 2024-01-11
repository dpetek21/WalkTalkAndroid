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
import hr.foi.rampu.walktalk.adapters.ExploreEventsAdapter
import hr.foi.rampu.walktalk.database.DatabaseFriend
import hr.foi.rampu.walktalk.entities.Event
import kotlinx.coroutines.launch

class ExploreEventsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            val exploreEventsList : List<Event> = DatabaseFriend.getPublicEvents()
            recyclerView = view.findViewById(R.id.rv_explore_events)
            recyclerView.adapter = ExploreEventsAdapter(exploreEventsList)
            recyclerView.layoutManager = LinearLayoutManager(view.context)
        }
    }

    override fun onResume() {
        super.onResume()

    }
}