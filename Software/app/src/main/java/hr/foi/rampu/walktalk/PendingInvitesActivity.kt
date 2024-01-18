package hr.foi.rampu.walktalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.walktalk.adapters.PendingInvitesAdapter
import hr.foi.rampu.walktalk.database.DatabaseEvent

class PendingInvitesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pending_invites)

        recyclerView = findViewById(R.id.rv_pending_invites)
        recyclerView.adapter = PendingInvitesAdapter(DatabaseEvent.event!!.invites!!)
        recyclerView.layoutManager = LinearLayoutManager(baseContext)
    }
}