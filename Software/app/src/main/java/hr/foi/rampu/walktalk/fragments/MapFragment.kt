package hr.foi.rampu.walktalk.fragments
import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants
import org.osmdroid.config.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hr.foi.rampu.walktalk.R
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.OverlayItem

class MapFragment: Fragment() {
    private lateinit var mapView: MapView
    private lateinit var markerOverlay: ItemizedIconOverlay<OverlayItem>
    public var SettingPoint = 0;
    public var currentMarker: OverlayItem? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = view.findViewById(R.id.mapView)
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        Configuration.getInstance().userAgentValue = "WalkTalk"
        mapView.setBuiltInZoomControls(true)
        mapView.setMultiTouchControls(true)

        markerOverlay = ItemizedIconOverlay<OverlayItem>(requireContext(), mutableListOf(), null)


        val mapEventsOverlay = MapEventsOverlay(object : MapEventsReceiver {
            override fun longPressHelper(p: GeoPoint?): Boolean {
                return false
            }

            override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                if (SettingPoint==1) {
                    handleTap(p)
                    return true
                }
                return false
            }
        })
        mapView.overlays.add(markerOverlay)
        mapView.overlays.add(mapEventsOverlay)
    }
    fun handleTap(geoPoint: GeoPoint?) {
        if (geoPoint != null) {
            if (currentMarker == null) {
                currentMarker = OverlayItem("Wanted position", "", geoPoint)
                currentMarker?.setMarker(resources.getDrawable(R.drawable.start_marker_icon))
                markerOverlay.addItem(currentMarker)
            } else {
                markerOverlay.removeItem(currentMarker)
                currentMarker = OverlayItem("Wanted position", "", geoPoint)
                currentMarker?.setMarker(resources.getDrawable(R.drawable.start_marker_icon))
                markerOverlay.addItem(currentMarker)
            }

            mapView.invalidate()
        }
    }
    fun addMarker(geoPoint: GeoPoint, title: String) {
        val overlayItem = OverlayItem(title, "", geoPoint)
        if(title=="Start Point"){
            overlayItem.setMarker(resources.getDrawable(R.drawable.start_marker_icon))
        }else if(title=="End Point"){
            overlayItem.setMarker(resources.getDrawable(R.drawable.end_marker_icon))
        }
        markerOverlay.addItem(overlayItem)
        mapView.invalidate()
    }
    fun clearMarkers(){
        markerOverlay.removeAllItems()
        mapView.invalidate()
    }
    fun requestPointSetting() {
        this.SettingPoint=1;
    }
    public fun zoomToPoints(point1: GeoPoint, point2: GeoPoint) {
        mapView.controller.setZoom(13)
        val avgLat = (point1.latitude + point2.latitude) / 2
        val avgLon = (point1.longitude + point2.longitude) / 2
        mapView.controller.animateTo(GeoPoint(avgLat,avgLon))
    }
    public fun zoomToWorldMap() {
        mapView.controller.setZoom(3)
        mapView.controller.animateTo(GeoPoint(54.6872,25.2797))
    }
}