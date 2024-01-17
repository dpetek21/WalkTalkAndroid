package hr.foi.rampu.walktalk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.walktalk.adapters.RoutesAdapter
import hr.foi.rampu.walktalk.fragments.MapFragment
import hr.foi.rampu.walktalk.fragments.RoutesFragment
import hr.foi.rampu.walktalk.navigation.NavigationSetup

class RoutesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routes)
        setFragment()

        NavigationSetup.SetupNavigationDrawer(this)
    }
    override fun onResume() {
        super.onResume()
        setFragment()
    }
    private fun setFragment(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, RoutesFragment())
            .commit()
    }
}