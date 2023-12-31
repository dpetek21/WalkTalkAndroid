package hr.foi.rampu.walktalk.entities
import org.osmdroid.util.GeoPoint
data class Route(
    val name: String,
    val start:GeoPoint,
    val end:GeoPoint,
    val rating:Int
){
    constructor(name: String, startLatitude: Double, startLongitude: Double, endLatitude: Double, endLongitude: Double, rating: Int)
            : this(name, GeoPoint(startLatitude, startLongitude), GeoPoint(endLatitude, endLongitude), rating)
}
