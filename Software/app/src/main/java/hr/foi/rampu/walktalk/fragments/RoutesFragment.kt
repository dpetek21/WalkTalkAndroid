package hr.foi.rampu.walktalk.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.walktalk.R
import hr.foi.rampu.walktalk.RouteDetailsActivity
import hr.foi.rampu.walktalk.RoutesActivity
import hr.foi.rampu.walktalk.adapters.RoutesAdapter
import hr.foi.rampu.walktalk.entities.Route
import hr.foi.rampu.walktalk.firebaseHandler.RouteHandler
import hr.foi.rampu.walktalk.firebaseHandler.UserDataContainer
import hr.foi.rampu.walktalk.helpers.MockRoute

class RoutesFragment : Fragment(){
    private val mockRoutes = MockRoute.giveMockRoutes()
    lateinit var Routes :List<Route>
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_routes, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.rv_routes)

        var handler = RouteHandler()
        handler.getRoutesOfOwner(UserDataContainer.username){routes ->
            Routes=routes
            val routesAdapter = RoutesAdapter(Routes) { clickedRoute ->
                val intent = Intent(requireContext(), RouteDetailsActivity::class.java)
                intent.putExtra("routeID", clickedRoute.id)
                intent.putExtra("routeName", clickedRoute.name)
                intent.putExtra("routeOwner", clickedRoute.owner)
                intent.putExtra("routeRating", clickedRoute.rating)
                intent.putExtra("routeStartLatitude", clickedRoute.start.latitude)
                intent.putExtra("routeStartLongitude", clickedRoute.start.longitude)
                intent.putExtra("routeEndLatitude", clickedRoute.end.latitude)
                intent.putExtra("routeEndLongitude", clickedRoute.end.longitude)
                requireContext().startActivity(intent)
            }
            recyclerView.adapter = routesAdapter
            recyclerView.layoutManager = LinearLayoutManager(view.context)
        }

    }
}