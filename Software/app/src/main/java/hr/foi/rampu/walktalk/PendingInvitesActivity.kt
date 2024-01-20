package hr.foi.rampu.walktalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.walktalk.adapters.PendingInvitesAdapter
import hr.foi.rampu.walktalk.database.DatabaseEvent

class PendingInvitesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var noInvites : TextView
    private lateinit var backButton : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pending_invites)

        recyclerView = findViewById(R.id.rv_pending_invites)
        noInvites = findViewById(R.id.txtv_no_invites)
        backButton = findViewById(R.id.imgv_invites_back_arrow)

        backButton.setOnClickListener {
            this.finish()
        }

        if (DatabaseEvent.event!!.pendingInvites != null) {
            recyclerView.visibility = VISIBLE
            noInvites.visibility = GONE
            recyclerView.adapter = PendingInvitesAdapter(DatabaseEvent.event!!.pendingInvites!!)
            recyclerView.layoutManager = LinearLayoutManager(baseContext)
        }
        else {
            recyclerView.visibility = GONE
            noInvites.visibility = VISIBLE
        }
    }
}