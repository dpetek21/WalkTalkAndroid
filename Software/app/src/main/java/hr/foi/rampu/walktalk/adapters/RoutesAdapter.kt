package hr.foi.rampu.walktalk.adapters
import android.graphics.Color
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.walktalk.R
import hr.foi.rampu.walktalk.entities.Route

class RoutesAdapter(private val items: List<Route>, private val onItemClick: (Route) -> Unit) :
    RecyclerView.Adapter<RoutesAdapter.RouteViewHolder>() {

    inner class RouteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //private val routeTextView: TextView = itemView.findViewById(R.id.rv_routes)
        private val routeName: TextView
        private val routeOwner: TextView
        private val routeRatingColor:SurfaceView
        init {
            routeName = itemView.findViewById(R.id.tv_route_name)
            routeOwner = itemView.findViewById(R.id.tv_route_owner)
            routeRatingColor = itemView.findViewById(R.id.sv_route_color)
        }

        fun bind(item: Route) {
            routeName.text = item.name
            if(item.rating==1){
                routeOwner.text = "Easy"
                routeRatingColor.setBackgroundColor(Color.GREEN)
            }else if(item.rating==2){
                routeOwner.text = "Average"
                routeRatingColor.setBackgroundColor(Color.YELLOW)
            }else if(item.rating==3){
                routeOwner.text="Hard"
                routeRatingColor.setBackgroundColor(Color.RED)
            }


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