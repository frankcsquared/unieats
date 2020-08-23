package com.example.unieats

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.unieats.models.Location
import com.example.unieats.models.User
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.math.log

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, OnMarkerClickListener, OnMapClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var imgBtn: ImageButton
    private lateinit var restaurantBtn: Button
    private var selectedId: Int = -1

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

        imgBtn = findViewById(R.id.imageButton2)
        restaurantBtn = findViewById(R.id.restaurant_button)

        mMap = googleMap
        //firebase stuff
        val ref = FirebaseDatabase.getInstance().reference.child("Location")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (childSnapshot in dataSnapshot.children) {
                    Log.e("ASD", "ASD")
                    var asd = childSnapshot.getValue(Location::class.java)
                    Log.e("MAPS", asd!!.id.toString())
                    ////val select = childSnapshot.getValue(User::class.java)
                    val tempId = asd!!.id.removePrefix("loc")

                    val ll = LatLng(asd.lat,asd.lon)
                    makePin (ll, tempId.toInt(), asd!!.name)

                }


            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //Toast.makeText(this@SearchFragment, "error error", Toast.LENGTH_LONG).show()
            }
        })
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(43.2624, -79.9201), 15.0f))

/*
        // Add markers and move the camera to Centro
        val centro = LatLng(43.2624, -79.9201)
        makePin (centro, 0, "Centro")
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centro, 15.0f))

        val lapiazza = LatLng(43.2637, -79.9171)
        makePin (lapiazza, 1, "La Piazza")

        val bistro = LatLng(43.2643, -79.9165)
        makePin (bistro, 2, "Bistro")

        val bridges = LatLng(43.2603, -79.9208)
        makePin (bridges, 3, "Bridges")
        //mMap.addMarker(MarkerOptions().position(bridges).title("Bridges Cafe"))
        // kept for now for reference

        val bymac = LatLng(43.2654, -79.9153)
        makePin (bymac, 4, "Bymac")
        //mMap.addMarker(MarkerOptions().position(bymac).title("Bymac"))

        val cafeone = LatLng(43.2613, -79.9163)
        makePin (cafeone, 5, "Cafe One")
        //mMap.addMarker(MarkerOptions().position(cafeone).title("Cafe One"))

        val caffeine = LatLng(43.2622, -79.9202)
        makePin (caffeine, 6, "CaFFeINe the Elements")
        //mMap.addMarker(MarkerOptions().position(caffeine).title("CaFFeINe the Elements"))

        val ecafe = LatLng(43.2586, -79.9196)
        makePin (ecafe, 7, "E-Cafe")
        //mMap.addMarker(MarkerOptions().position(ecafe).title("E-Cafe"))

 */

        mMap.setOnMarkerClickListener(this)
        mMap.setOnMapClickListener {
            restaurantBtn.text = "Nothing selected"
        }

        imgBtn.setOnClickListener {
            finish()
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
        }

        restaurantBtn.setOnClickListener {

            if (restaurantBtn.text != "Nothing selected") {
                val intent = Intent (this, LogActivity::class.java)
                intent.putExtra("chosenId", selectedId)

                startActivity(intent)
            }

            else {
                Toast.makeText(
                    applicationContext,
                    "choose a location!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    override fun onMarkerClick(marker: Marker): Boolean {

        restaurantBtn.text = marker.title
        selectedId = marker.tag as Int
        Log.e("ID", selectedId.toString())

        return false
    }

    private fun makePin (location:LatLng, id:Int, title:String) {
        val marker:Marker = mMap.addMarker(MarkerOptions().position(location).title(title))
        marker.tag = id
    }

    override fun onMapClick(p0: LatLng?) {
        restaurantBtn.text = "Nothing selected"
        selectedId = -1
    }


}

