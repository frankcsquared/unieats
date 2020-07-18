package com.example.unieats.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.unieats.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlin.random.Random

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var graphView: GraphView
    private lateinit var mSeries1: LineGraphSeries<DataPoint>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        //initialize graph
        val graph = view.findViewById(R.id.graph) as GraphView
        //graph.addSeries(mSeries1)

        //Initialize database and read
        val ref = FirebaseDatabase.getInstance().reference.child("Food")
        var myIntList: MutableList<Double?> = mutableListOf<Double?>() // calories
        var cals: Array<Double?> = myIntList.toTypedArray();


        // Read from the database
        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (childSnapshot in dataSnapshot.children) {

                    myIntList.add(childSnapshot.child("calories").getValue(Double::class.java))

                    cals = myIntList.toTypedArray()
                }

                // create graph
                val series: LineGraphSeries<DataPoint> = LineGraphSeries(
                    arrayOf(
                        DataPoint(0.0, myIntList[0]!!),
                        DataPoint(1.0, myIntList[1]!!),
                        DataPoint(2.0, myIntList[2]!!),
                        DataPoint(3.0, myIntList[3]!!),
                        DataPoint(4.0, myIntList[4]!!)
                    )
                )

                // Add series above to graph
                graph.addSeries(series);
            }
            override fun onCancelled(error: DatabaseError) {
                //Failed to read value
                //Toast.makeText(this@SearchFragment, "error error", Toast.LENGTH_LONG).show()
            }
        })

        return view
    }

    private fun generateData(): Array<DataPoint?>? {
        val count = 30
        val values =
            arrayOfNulls<DataPoint>(count)
        for (i in 0 until count) {
            val x = i.toDouble()
            val f: Double = Random.nextDouble() * 0.15 + 0.3
            val y: Double = Math.sin(i * f + 2) + Random.nextDouble() * 0.3
            val v =
                DataPoint(x, y)
            values[i] = v
        }
        return values
    }
}