package hr.foi.rampu.walktalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hr.foi.rampu.walktalk.navigation.NavigationSetup

class EventsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        NavigationSetup.SetupNavigationDrawer(this)
    }
}