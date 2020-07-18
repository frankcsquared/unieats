package com.example.unieats

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker on Centro and move the camera
        val centro = LatLng(43.2624, -79.9201)
        mMap.addMarker(MarkerOptions().position(centro).title("Centro"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centro, 15.0f))

        val lapiazza = LatLng(43.2637, -79.9171)
        mMap.addMarker(MarkerOptions().position(lapiazza).title("La Piazza"))

        val bistro = LatLng(43.2643, -79.9165)
        makePin (bistro, 0, "bistro")

        val bridges = LatLng(43.2603, -79.9208)
        mMap.addMarker(MarkerOptions().position(bridges).title("Bridges Cafe"))

        val bymac = LatLng(43.2654, -79.9153)
        mMap.addMarker(MarkerOptions().position(bymac).title("Bymac"))

        val cafeone = LatLng(43.2613, -79.9163)
        mMap.addMarker(MarkerOptions().position(cafeone).title("Cafe One"))

        val caffeine = LatLng(43.2622, -79.9202)
        mMap.addMarker(MarkerOptions().position(caffeine).title("CaFFeINe the Elements"))

        val ecafe = LatLng(43.2586, -79.9196)
        mMap.addMarker(MarkerOptions().position(ecafe).title("E-Cafe"))

        mMap.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        Toast.makeText(this,
            marker.tag.toString(),
            Toast.LENGTH_SHORT).show();
        return false
    }

    private fun makePin (location:LatLng, id:Int, title:String) {
        val marker:Marker = mMap.addMarker(MarkerOptions().position(location).title(title))
        marker.tag = id
    }

}

