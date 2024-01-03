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
import hr.foi.rampu.walktalk.RoutesActivity
import hr.foi.rampu.walktalk.adapters.RoutesAdapter
import hr.foi.rampu.walktalk.helpers.MockRoute

class RoutesFragment : Fragment(){
    private val mockTasks = MockRoute.giveMockRoutes()
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_routes, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.rv_routes)
        val routesAdapter = RoutesAdapter(mockTasks) { clickedRoute ->
            val intent = Intent(requireContext(), RoutesActivity::class.java)
            intent.putExtra("Route", clickedRoute.name)
            intent.putExtra("routeOwner", clickedRoute.owner)
            requireContext().startActivity(intent)
        }
        recyclerView.adapter = routesAdapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)

    }
}