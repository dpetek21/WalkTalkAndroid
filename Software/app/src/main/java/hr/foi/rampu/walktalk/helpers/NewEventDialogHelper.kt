package hr.foi.rampu.walktalk.helpers

import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker
import hr.foi.rampu.walktalk.R
import hr.foi.rampu.walktalk.database.DatabaseFriend
import hr.foi.rampu.walktalk.entities.Event
import java.text.SimpleDateFormat
import java.util.Locale

class NewEventDialogHelper(private val view: View) {
    private val sdfDate = SimpleDateFormat("dd.MM.yyyy.", Locale.US)
    private val spinnerPace = view.findViewById<Spinner>(R.id.spn_pace)
    private val etEventName = view.findViewById<EditText>(R.id.et_new_event_name)
    private val spinnerRoute = view.findViewById<Spinner>(R.id.spn_route)
    private val dateSelection = view.findViewById<EditText>(R.id.et_event_date)

    fun populateSpinnerPace(paces: List<String>) {
        val spinnerAdapter = ArrayAdapter(
            view.context,
            android.R.layout.simple_spinner_item,
            paces)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPace.adapter = spinnerAdapter
    }

    private fun buildEvent() : Event
    {
        val eventName = etEventName.text.toString()
        val pace = spinnerPace.selectedItem as String


        return Event(eventName,0.0,0, pace, sdfDate.parse(dateSelection.getText().toString())!!, DatabaseFriend.username,null,true)
    }

    fun activateDateListener(supportFragmentManager: FragmentManager) {
        dateSelection.setOnFocusChangeListener{view, hasFocus ->
            if (hasFocus) {
                val picker = MaterialDatePicker.Builder
                    .datePicker()
                    .setTitleText("Select date of the event:")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
                picker.show(supportFragmentManager, "EVENT_DATE")
                picker.addOnPositiveButtonClickListener {
                    dateSelection.setText(sdfDate.format(it).toString())
                    val newEvent = buildEvent()
                    DatabaseFriend.addNewEvent(newEvent)
                }
                view.clearFocus()
            }
        }
    }
}