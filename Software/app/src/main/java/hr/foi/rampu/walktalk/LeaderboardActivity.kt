package hr.foi.rampu.walktalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import hr.foi.rampu.walktalk.adapters.FriendsPagerAdapter
import hr.foi.rampu.walktalk.adapters.LeaderboardPagerAdapter
import hr.foi.rampu.walktalk.database.DatabaseFriend
import hr.foi.rampu.walktalk.navigation.NavigationSetup
import kotlinx.coroutines.launch

class LeaderboardActivity : AppCompatActivity() {

    private lateinit var tabLayout : TabLayout
    private lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        tabLayout = findViewById(R.id.tabLayoutLeaderboard)
        viewPager = findViewById(R.id.vp2Leaderboard)

        viewPager.adapter = LeaderboardPagerAdapter(supportFragmentManager,lifecycle)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.day_leaderboard)
                }
                1 -> {
                    tab.text = getString(R.string.week_leaderboard)
                }
                2 -> {
                    tab.text = getString(R.string.month_leaderboard)
                }
            }
        }.attach()

        NavigationSetup.SetupNavigationDrawer(this)

    }

}