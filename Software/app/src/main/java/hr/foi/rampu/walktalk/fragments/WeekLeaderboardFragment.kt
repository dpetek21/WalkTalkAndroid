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
import hr.foi.rampu.walktalk.adapters.LeaderboardRecyclerViewAdapter
import hr.foi.rampu.walktalk.database.LeaderboardDAO
import kotlinx.coroutines.launch

class WeekLeaderboardFragment : Fragment() {


    private lateinit var leadeboardDAO: LeaderboardDAO
    private lateinit var recyclerView: RecyclerView
    private lateinit var leaderboardAdapter: LeaderboardRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_week_leaderboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        leadeboardDAO = LeaderboardDAO()
        recyclerView = view.findViewById(R.id.rv_week_leaderboard)

        lifecycleScope.launch {
            val leaderboardItems = leadeboardDAO.getStepsLogs("week")
            val leaderboardItemsSorted = leaderboardItems.sortedByDescending { it.steps }
            leaderboardAdapter = LeaderboardRecyclerViewAdapter(leaderboardItemsSorted)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = leaderboardAdapter
        }
    }

}