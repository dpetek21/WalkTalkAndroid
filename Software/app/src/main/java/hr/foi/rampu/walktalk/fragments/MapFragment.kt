package hr.foi.rampu.walktalk.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hr.foi.rampu.walktalk.R
import org.osmdroid.api.IGeoPoint
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.OverlayItem
class MapFragment: Fragment() {
    private lateinit var mapView: MapView
    private lateinit var markerOverlay: ItemizedIconOverlay<OverlayItem>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = view.findViewById(R.id.mapView)
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)

        mapView.setBuiltInZoomControls(true)
        mapView.setMultiTouchControls(true)

        markerOverlay = ItemizedIconOverlay<OverlayItem>(requireContext(), mutableListOf(), null)

        mapView.overlays.add(markerOverlay)
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
}