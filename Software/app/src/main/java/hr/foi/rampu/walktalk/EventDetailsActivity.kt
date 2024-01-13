package hr.foi.rampu.walktalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import hr.foi.rampu.walktalk.database.DatabaseEvent
import hr.foi.rampu.walktalk.database.DatabaseFriend
import hr.foi.rampu.walktalk.helpers.NewEventDialogHelper
import hr.foi.rampu.walktalk.helpers.Pace
import kotlinx.coroutines.launch

class EventDetailsActivity : AppCompatActivity() {
    private lateinit var backButton: ImageView
    private lateinit var eventName: TextView
    private lateinit var cardViewEventDetails: CardView
    private lateinit var numberOfPeople: TextView
    private lateinit var numberOfKilometers: TextView
    private lateinit var eventDate: TextView
    private lateinit var pace: TextView
    private lateinit var actionButton: Button
    private lateinit var cancelEventButton : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        cardViewEventDetails = findViewById(R.id.cv_event_detail)
        backButton = findViewById(R.id.img_event_details_back_arrow)
        eventName = findViewById(R.id.txt_event_details)
        numberOfPeople = findViewById(R.id.txtv_event_detail_people)
        numberOfKilometers = findViewById(R.id.txtv_event_detail_kilometres)
        eventDate = findViewById(R.id.txtv_event_detail_date)
        pace = findViewById(R.id.txtv_event_detail_pace)
        cancelEventButton = findViewById(R.id.imgBtn_cancel_event)


        /*
        eventName.text = intent.getStringExtra("event_name")
        numberOfPeople.text =
            getString(R.string.number_of_people_going, intent.getStringExtra("event_people").toString())
        numberOfKilometers.text = getString(R.string.number_of_kilometers,intent.getStringExtra("event_kilometers"))
        eventDate.text = intent.getStringExtra("event_date").toString()
        pace.text = getString(R.string.pace,intent.getStringExtra("event_pace"))
         */
        backButton.setOnClickListener{
            this.finish()
        }
        val event = DatabaseEvent.event!!
        eventName.text = event.name
        numberOfPeople.text = getString(R.string.number_of_people_going,event.numberOfPeople.toString())
        numberOfKilometers.text = getString(R.string.number_of_kilometers,event.numberOfKilometers.toString())
        eventDate.text = event.date.toString()
        pace.text = getString(R.string.pace,event.pace)


        actionButton = findViewById(R.id.btn_event_detail_action_button)
        if (DatabaseFriend.username == event.organizer) {
            actionButton.text = getString(R.string.start_event)
            cancelEventButton.visibility  = VISIBLE

            cardViewEventDetails.setOnClickListener {

            }

            cancelEventButton.setOnClickListener {
                lifecycleScope.launch {
                    DatabaseEvent.cancelEvent()
                }
                this.finish()
            }
        }

        else if (!DatabaseEvent.checkIfUserSentInvite()) {
            actionButton.text = getString(R.string.send_invitation)
            actionButton.setOnClickListener {
                    lifecycleScope.launch {
                        val inviteSent = DatabaseEvent.sendInvite()
                        if (inviteSent) {
                            actionButton.text = getString(R.string.invite_sent)
                            actionButton.setBackgroundColor(getColor(R.color.beige))
                            actionButton.setTextColor(getColor(R.color.black))
                            actionButton.isClickable = false
                        }
                        else {
                            actionButton.text = getString(R.string.retry)
                        }
                    }
                }
        }
        else {
            actionButton.text = getString(R.string.waiting_for_approval)
            actionButton.setBackgroundColor(getColor(R.color.beige))
            actionButton.setTextColor(getColor(R.color.black))
            actionButton.alpha = 0.8F
            actionButton.isClickable = false
        }
    }

    private fun showDialog() {
        val newTaskDialogView = LayoutInflater
            .from(baseContext)
            .inflate(R.layout.create_event_dialog, null)
        AlertDialog.Builder(this)
            .setView(newTaskDialogView)
            .setTitle(getString(R.string.update_event))
            .setPositiveButton("Update",null)
            .setNegativeButton("Cancel",null)
            .create()
            .show()

        val dialogHelper = NewEventDialogHelper(newTaskDialogView)
        dialogHelper.populateSpinnerPace(Pace.getAllPaces())
        dialogHelper.activateDateListener(supportFragmentManager)
    }


}