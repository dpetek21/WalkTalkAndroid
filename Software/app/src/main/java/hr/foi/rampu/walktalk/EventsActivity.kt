package hr.foi.rampu.walktalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import hr.foi.rampu.walktalk.adapters.EventsPagerAdapter
import hr.foi.rampu.walktalk.helpers.NewEventDialogHelper
import hr.foi.rampu.walktalk.helpers.Pace
import hr.foi.rampu.walktalk.navigation.NavigationSetup

class EventsActivity : AppCompatActivity() {
    private lateinit var tabLayout : TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var fab : FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        NavigationSetup.SetupNavigationDrawer(this)

        tabLayout = findViewById(R.id.tl_explore_going)
        viewPager = findViewById(R.id.vp2_events)
        fab = findViewById(R.id.fab_create_event)
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

        fab.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val newTaskDialogView = LayoutInflater
            .from(baseContext)
            .inflate(R.layout.create_event_dialog, null)
        AlertDialog.Builder(this)
            .setView(newTaskDialogView)
            .setTitle(getString(R.string.create_new_event))
            .setPositiveButton("Create",null)
            .setNegativeButton("Cancel",null)
            .show()

        val dialogHelper = NewEventDialogHelper(newTaskDialogView)
        dialogHelper.populateSpinnerPace(Pace.getAllPaces())
        dialogHelper.activateDateListener(supportFragmentManager)
    }
}