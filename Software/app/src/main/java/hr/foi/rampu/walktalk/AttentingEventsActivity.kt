package hr.foi.rampu.walktalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hr.foi.rampu.walktalk.navigation.NavigationSetup

class AttentingEventsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attenting_events)
        NavigationSetup.SetupNavigationDrawer(this)
    }
}