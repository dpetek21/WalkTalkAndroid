package hr.foi.rampu.walktalk.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.walktalk.R
import hr.foi.rampu.walktalk.entities.Route

class RoutesAdapter(private val items: List<Route>, private val onItemClick: (Route) -> Unit) :
    RecyclerView.Adapter<RoutesAdapter.RouteViewHolder>() {

    inner class RouteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val routeTextView: TextView = itemView.findViewById(R.id.routeTextView)

        fun bind(item: Route) {
            routeTextView.text = item.name

            itemView.setOnClickListener { onItemClick.invoke(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.routes_item, parent, false)

        return RouteViewHolder(view)
    }

    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}