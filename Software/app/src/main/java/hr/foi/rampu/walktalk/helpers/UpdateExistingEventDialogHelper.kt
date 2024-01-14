package hr.foi.rampu.walktalk.helpers

import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker
import hr.foi.rampu.walktalk.R
import hr.foi.rampu.walktalk.database.DatabaseEvent
import java.text.SimpleDateFormat
import java.util.Locale

class UpdateExistingEventDialogHelper(private val view: View) {
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

        try {
            val currentPaceIndex = paces.indexOf(DatabaseEvent.event!!.pace)

            spinnerPace.setSelection(currentPaceIndex)
        }
        catch (error : Exception) {
            Log.e("PACE_INDEX_ERROR", error.message.toString())
            return
        }

    }


    init {
        etEventName.setText(DatabaseEvent.event!!.name)
        dateSelection.setText(sdfDate.format(DatabaseEvent.event!!.date!!))
    }
/*
    private fun updateEvent() : Event? {
        return try {
            val name = etEventName.text.toString()
            val date = sdfDate.parse(dateSelection.text.toString())
            val pace = spinnerPace.selectedItem as String

            Event(name,0.0,0,pace,date,DatabaseEvent.event!!.organizer,null,true,DatabaseEvent.event!!.invites)
        } catch (error : Exception) {
            Log.e("UPDATE_EVENT_ERROR",error.message.toString())
            null
        }
    }

 */

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
                }
                view.clearFocus()
            }
        }
    }
}