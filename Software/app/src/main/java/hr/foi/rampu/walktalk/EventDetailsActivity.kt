package hr.foi.rampu.walktalk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import hr.foi.rampu.walktalk.database.ChatsListDAO
import hr.foi.rampu.walktalk.database.DatabaseEvent
import hr.foi.rampu.walktalk.database.DatabaseEvent.event
import hr.foi.rampu.walktalk.entities.Event
import hr.foi.rampu.walktalk.entities.Route
import hr.foi.rampu.walktalk.firebaseHandler.RouteHandler
import hr.foi.rampu.walktalk.firebaseHandler.UserDataContainer
import hr.foi.rampu.walktalk.fragments.MapFragment
import hr.foi.rampu.walktalk.helpers.Pace
import hr.foi.rampu.walktalk.helpers.UpdateExistingEventDialogHelper
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

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
    private lateinit var seeInvitesButton : ImageButton
    private lateinit var chatsListDAO: ChatsListDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        chatsListDAO = ChatsListDAO()

        val event = DatabaseEvent.event!!

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.event_detail_map_container,MapFragment())
            .addToBackStack(null)
            .commit()

        cardViewEventDetails = findViewById(R.id.cv_event_detail)
        backButton = findViewById(R.id.img_event_details_back_arrow)
        eventName = findViewById(R.id.txt_event_details)
        numberOfPeople = findViewById(R.id.txtv_event_detail_people)
        numberOfKilometers = findViewById(R.id.txtv_event_detail_kilometres)
        eventDate = findViewById(R.id.txtv_event_detail_date)
        pace = findViewById(R.id.txtv_event_detail_pace)
        cancelEventButton = findViewById(R.id.imgBtn_cancel_event)
        seeInvitesButton = findViewById(R.id.imgBtn_see_invites)
        backButton.setOnClickListener {
            this.finish()
        }
        populateEventData()


        actionButton = findViewById(R.id.btn_event_detail_action_button)
        if (UserDataContainer.username == event.organizer) {
            actionButton.text = getString(R.string.start_event)
            cancelEventButton.visibility  = VISIBLE
            seeInvitesButton.visibility = VISIBLE

            seeInvitesButton.setOnClickListener {
                val intent = Intent(this,PendingInvitesActivity::class.java)
                startActivity(intent)
            }

            cardViewEventDetails.setOnClickListener {
                    this.showDialog()
            }

            cancelEventButton.setOnClickListener {
                lifecycleScope.launch {
                    DatabaseEvent.cancelEvent()
                }
                this.finish()
            }
        }

        else if (DatabaseEvent.checkIfUserAccepted()) {
            actionButton.text = getString(R.string.accepted)
            actionButton.isClickable = false
            actionButton.setBackgroundColor(getColor(R.color.green))
            actionButton.setTextColor(getColor(R.color.white))
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

    private fun populateEventData() {
        val sdfDate = SimpleDateFormat("dd.MM.yyyy.", Locale.US)
        val event = DatabaseEvent.event!!
        eventName.text = event.name
        numberOfPeople.text = getString(R.string.number_of_people_going,event.acceptedInvites?.size ?: 0.toString() )
        numberOfKilometers.text = getString(R.string.number_of_kilometers,event.numberOfKilometers.toString())
        eventDate.text = sdfDate.format(event.date!!)
        pace.text = getString(R.string.pace,event.pace)
        return
    }

    private fun showDialog() {
        val updateEventDialogView = LayoutInflater
            .from(baseContext)
            .inflate(R.layout.create_event_dialog, null)
         AlertDialog.Builder(this)
            .setView(updateEventDialogView)
            .setTitle(getString(R.string.update_event))
            .setPositiveButton("Update") {dialog,_ ->
                val eventName : EditText = updateEventDialogView.findViewById(R.id.et_new_event_name)
                val dateSelection = updateEventDialogView.findViewById<EditText>(R.id.et_event_date)
                if (eventName.text.isEmpty() || dateSelection.text.isEmpty()) {
                    Log.i("EVENT_NAME_EMPTY", "Event name  or date is empty")
                } else {
                    val sdfDate = SimpleDateFormat("dd.MM.yyyy.", Locale.US)
                    val spinnerPace = updateEventDialogView.findViewById<Spinner>(R.id.spn_pace)
                    val spinnerRoute = updateEventDialogView.findViewById<Spinner>(R.id.spn_route)
                    val event = Event(eventName.text.toString(),
                        0.0,
                        spinnerPace.selectedItem as String,
                        sdfDate.parse(dateSelection.text.toString()),
                        UserDataContainer.username,
                        spinnerRoute.selectedItem as Route,
                        true,
                        DatabaseEvent.event!!.pendingInvites,
                        DatabaseEvent.event!!.acceptedInvites
                    )
                    lifecycleScope.launch {
                        DatabaseEvent.updateEvent(event)
                        populateEventData()
                        populateMap()
                    }
                    dialog.dismiss()

                }
                return@setPositiveButton
            }
            .setNegativeButton("Cancel",null)
            .create()
            .show()

        val dialogHelper = UpdateExistingEventDialogHelper(updateEventDialogView)
        val routeHandler = RouteHandler()
        routeHandler.getRoutesOfOwner(UserDataContainer.username, dialogHelper::populateSpinnerRoute)
        dialogHelper.populateSpinnerPace(Pace.getAllPaces())
        dialogHelper.activateDateListener(supportFragmentManager)

    }
    private fun populateMap() {
        val mapFragment =  supportFragmentManager.findFragmentById(R.id.event_detail_map_container) as MapFragment?
        if (mapFragment != null) {
            mapFragment.clearMarkers()
            if (event!!.route != null) {
                mapFragment.addMarker(event!!.route!!.start,"Start Point")
                mapFragment.addMarker(event!!.route!!.end,"End Point")
                mapFragment.zoomToPoints(event!!.route!!.start,event!!.route!!.end)
            }
            else {
                mapFragment.zoomToWorldMap()
            }
        }
    }
    override fun onResume() {
        super.onResume()
        populateMap()
    }


}