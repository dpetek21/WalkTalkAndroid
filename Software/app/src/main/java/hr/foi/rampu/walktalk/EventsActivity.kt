package hr.foi.rampu.walktalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import hr.foi.rampu.walktalk.adapters.EventsPagerAdapter
import hr.foi.rampu.walktalk.navigation.NavigationSetup

class EventsActivity : AppCompatActivity() {
    private lateinit var tabLayout : TabLayout
    private lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        NavigationSetup.SetupNavigationDrawer(this)

        tabLayout = findViewById(R.id.tl_explore_going)
        viewPager = findViewById(R.id.vp2_events)
        viewPager.adapter = EventsPagerAdapter(supportFragmentManager,lifecycle)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.explore)
                }
                1 -> {
                    tab.text = getString(R.string.going)
                }
            }
        }.attach()
    }
}