package hr.foi.rampu.walktalk.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.walktalk.R
import hr.foi.rampu.walktalk.entities.Event

class ExploreEventsAdapter(val eventList: List<Event>) : RecyclerView.Adapter<ExploreEventsAdapter.ExploreEventsHolder>() {

    inner class ExploreEventsHolder(view : View) :RecyclerView.ViewHolder(view) {
        val eventName : TextView
        val kilometers: TextView
        val numberOfPeople: TextView
        val pace : TextView
        val date : TextView

        init {
            eventName = view.findViewById(R.id.txtv_event_name_list_item)
            kilometers = view.findViewById(R.id.txtv_kilometres)
            numberOfPeople = view.findViewById(R.id.txtv_people)
            pace = view.findViewById(R.id.txtv_pace)
            date = view.findViewById(R.id.txtv_date)
        }

        fun bind(event: Event) {
            eventName.text = event.name
            val kilo = event.numberOfKilometers.toString() + " km"
            kilometers.text = kilo
            numberOfPeople.text = event.numberOfPeople.toString()
            val pc = event.pace + " pace"
            pace.text = pc
            date.text = event.date.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreEventsHolder {
        val exploreEventsView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.event_list_item, parent, false)
        return ExploreEventsHolder(exploreEventsView)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    override fun onBindViewHolder(holder: ExploreEventsHolder, position: Int) {
        holder.bind(eventList[position])
    }


}