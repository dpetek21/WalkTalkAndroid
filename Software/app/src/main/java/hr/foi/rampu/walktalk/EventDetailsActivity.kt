package hr.foi.rampu.walktalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import hr.foi.rampu.walktalk.database.DatabaseEvent
import hr.foi.rampu.walktalk.database.DatabaseFriend
import kotlinx.coroutines.launch

class EventDetailsActivity : AppCompatActivity() {
    private lateinit var backButton: ImageView
    private lateinit var eventName: TextView
    private lateinit var numberOfPeople: TextView
    private lateinit var numberOfKilometers: TextView
    private lateinit var eventDate: TextView
    private lateinit var pace: TextView
    private lateinit var actionButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)


        backButton = findViewById(R.id.img_event_details_back_arrow)
        eventName = findViewById(R.id.txt_event_details)
        numberOfPeople = findViewById(R.id.txtv_event_detail_people)
        numberOfKilometers = findViewById(R.id.txtv_event_detail_kilometres)
        eventDate = findViewById(R.id.txtv_event_detail_date)
        pace = findViewById(R.id.txtv_event_detail_pace)


        /*
        eventName.text = intent.getStringExtra("event_name")
        numberOfPeople.text =
            getString(R.string.number_of_people_going, intent.getStringExtra("event_people").toString())
        numberOfKilometers.text = getString(R.string.number_of_kilometers,intent.getStringExtra("event_kilometers"))
        eventDate.text = intent.getStringExtra("event_date").toString()
        pace.text = getString(R.string.pace,intent.getStringExtra("event_pace"))
         */

        val event = DatabaseEvent.event!!
        eventName.text = event.name
        numberOfPeople.text = getString(R.string.number_of_people_going,event.numberOfPeople.toString())
        numberOfKilometers.text = getString(R.string.number_of_kilometers,event.numberOfKilometers.toString())
        eventDate.text = event.date.toString()
        pace.text = getString(R.string.pace,event.pace)


        actionButton = findViewById(R.id.btn_event_detail_action_button)
        if (DatabaseFriend.username == event.organizer) {
            actionButton.text = getString(R.string.start_event)
        }
        else {
            actionButton.text = getString(R.string.send_invitation)
                actionButton.setOnClickListener {
                    lifecycleScope.launch {
                        DatabaseEvent.sendInvite()
                    }
                }

        }
        backButton.setOnClickListener{
            this.finish()
        }
    }


}