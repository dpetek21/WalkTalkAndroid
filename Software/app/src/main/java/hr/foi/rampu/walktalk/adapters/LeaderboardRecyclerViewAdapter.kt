package hr.foi.rampu.walktalk.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.walktalk.R
import hr.foi.rampu.walktalk.entities.LeaderboardItem

class LeaderboardRecyclerViewAdapter(private val leaderboardList: List<LeaderboardItem>) :
    RecyclerView.Adapter<LeaderboardRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewLeaderboardName)
        val textViewSteps: TextView = itemView.findViewById(R.id.textViewLeaderboardSteps)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.leaderboard_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = leaderboardList[position]

        holder.textViewName.text = currentItem.name
        holder.textViewSteps.text = currentItem.steps.toString()
    }

    override fun getItemCount(): Int {
        return leaderboardList.size
    }
}