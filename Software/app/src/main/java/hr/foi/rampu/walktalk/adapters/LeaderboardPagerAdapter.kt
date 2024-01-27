package hr.foi.rampu.walktalk.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import hr.foi.rampu.walktalk.fragments.CurrentFriendsFragment
import hr.foi.rampu.walktalk.fragments.DayLeaderboardFragment
import hr.foi.rampu.walktalk.fragments.MonthLeaderboardFragment
import hr.foi.rampu.walktalk.fragments.PendingFriendsFragment
import hr.foi.rampu.walktalk.fragments.WeekLeaderboardFragment

class LeaderboardPagerAdapter(fragmentManager : FragmentManager, lifecycle : Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle)  {

    val fragments = listOf<Fragment>(DayLeaderboardFragment(), WeekLeaderboardFragment(), MonthLeaderboardFragment())
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}