package hr.foi.rampu.walktalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hr.foi.rampu.walktalk.fragments.MapFragment
import hr.foi.rampu.walktalk.fragments.RoutesFragment
import hr.foi.rampu.walktalk.navigation.NavigationSetup

class RouteDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_details)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, MapFragment())
            .commit()
        NavigationSetup.SetupNavigationDrawer(this)
    }
}