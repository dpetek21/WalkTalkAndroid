package hr.foi.rampu.walktalk.entities
import org.osmdroid.util.GeoPoint
data class Route(
    val id: String?,
    val name: String,
    val start:GeoPoint,
    val end:GeoPoint,
    val rating:Int,
    val owner:String
){
    constructor() : this(null, "", GeoPoint(0.0, 0.0), GeoPoint(0.0, 0.0), -1, "")
    constructor(id:String?,name: String, startLatitude: Double, startLongitude: Double, endLatitude: Double, endLongitude: Double, rating: Int,owner:String)
            : this(id,name, GeoPoint(startLatitude, startLongitude), GeoPoint(endLatitude, endLongitude), rating, owner)
    constructor(name: String, startLatitude: Double, startLongitude: Double, endLatitude: Double, endLongitude: Double, rating: Int,owner:String)
            : this(null,name, GeoPoint(startLatitude, startLongitude), GeoPoint(endLatitude, endLongitude), rating, owner)


}
