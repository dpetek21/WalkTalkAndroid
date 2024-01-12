package hr.foi.rampu.walktalk.helpers

import hr.foi.rampu.walktalk.entities.Route
import org.osmdroid.util.GeoPoint

object MockRoute {
    fun giveMockRoutes(): List<Route> {
        val route1 = Route("First route", GeoPoint(37.7749, -122.4194), GeoPoint(37.7899, -122.4094), 1, "dpetek21")
        val route2 = Route("Second route - harder", GeoPoint(34.0522, -118.2437), GeoPoint(34.0722, -118.2637), 2, "dpetek21")
        val route3 = Route("Third route - hardest", GeoPoint(40.7128, -74.0060), GeoPoint(40.7228, -74.0160), 3, "dpetek21")
        val newRoute = Route("ADD NEW ROUTE", GeoPoint(0.0, 0.0), GeoPoint(0.0, 0.0), -1, "dpetek21")

        return listOf(route1, route1, route2, route3, newRoute,)
    }
}