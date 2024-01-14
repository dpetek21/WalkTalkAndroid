package hr.foi.rampu.walktalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import hr.foi.rampu.walktalk.adapters.EventsPagerAdapter
import hr.foi.rampu.walktalk.database.DatabaseEvent
import hr.foi.rampu.walktalk.database.DatabaseFriend
import hr.foi.rampu.walktalk.entities.Event
import hr.foi.rampu.walktalk.helpers.NewEventDialogHelper
import hr.foi.rampu.walktalk.helpers.Pace
import hr.foi.rampu.walktalk.navigation.NavigationSetup
import java.text.SimpleDateFormat
import java.util.Locale

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
            .setPositiveButton("Create") {dialog,_ ->
                val eventName : EditText = newTaskDialogView.findViewById(R.id.et_new_event_name)
                if (eventName.text.isEmpty()) {
                    Log.i("EVENT_NAME_EMPTY", "Event name is empty")
                } else {
                    val sdfDate = SimpleDateFormat("dd.MM.yyyy.", Locale.US)
                    val spinnerPace = newTaskDialogView.findViewById<Spinner>(R.id.spn_pace)
                    val dateSelection = newTaskDialogView.findViewById<EditText>(R.id.et_event_date)
                    val event = Event(eventName.text.toString(),
                        0.0,
                        0,
                        spinnerPace.selectedItem as String,
                        sdfDate.parse(dateSelection.text.toString()),
                        DatabaseFriend.username,
                        null,
                        true,
                        null
                        )
                    dialog.dismiss()

                    DatabaseEvent.addNewEvent(event)
                }
                return@setPositiveButton
            }
            .setNegativeButton("Cancel",null)
            .create()
            .show()


        val dialogHelper = NewEventDialogHelper(newTaskDialogView)
        dialogHelper.populateSpinnerPace(Pace.getAllPaces())
        dialogHelper.activateDateListener(supportFragmentManager)
    }
}