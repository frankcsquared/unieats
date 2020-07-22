package com.example.unieats.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.unieats.MainActivity
import com.example.unieats.R
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var switch: Switch
    private var myDateList: MutableList<Int?> = mutableListOf<Int?>() // date
    private var myIntList: MutableList<Double?> = mutableListOf<Double?>() // calories

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
        val switch = view.findViewById(R.id.switch1) as Switch
        val calsText = view.findViewById(R.id.calsText) as TextView
        var myIntList: MutableList<Double?> = mutableListOf<Double?>() // calories
        var myCalList: MutableList<Double?> = mutableListOf<Double?>() // cals for the day

        // Read from the database
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (childSnapshot in dataSnapshot.children) {
                    myIntList.add(childSnapshot.child("calories").getValue(Double::class.java))
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

        // Read from the database
        ref2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var count: Double
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (childSnapshot in dataSnapshot.children) {
                    myCalList.add(childSnapshot.child("history").child("cals").getValue(Double::class.java))
                }
            }
            override fun onCancelled(error: DatabaseError) {
                //Failed to read value
                //Toast.makeText(this@SearchFragment, "error error", Toast.LENGTH_LONG).show()
            }
        })
        var myDateList: MutableList<Int?> = mutableListOf<Int?>() // date
        //set graph labels
        graph.getViewport().setMinX(20200718.0);
        graph.getViewport().setMaxX(20200724.0);
        graph.getViewport().setMinY(200.0);
        graph.getViewport().setMaxY(10000.0);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);

        graph.getGridLabelRenderer().setHorizontalLabelsAngle(135);

        Log.e("LENGTH " +  MainActivity.selectedUser.history.size, " SIZE")

        //inserts all things into map with date as index
        var graphMap = mutableMapOf<Int, Int>()
        for ((k, v) in MainActivity.selectedUser.history) {
            if(graphMap[v.date] == null) {
                graphMap[v.date] = 0
            }else{
                var addme = graphMap[v.date]!! + v.cals
                graphMap[v.date] = addme
            }
        }

        var dataPts = arrayOfNulls<DataPoint>(graphMap.size)

        var iterator = 0
        for ((k,v) in graphMap){
            dataPts[iterator] = DataPoint(k.toDouble(), v.toDouble())
            iterator+=1
        }
        // create graph
        val series: LineGraphSeries<DataPoint> = LineGraphSeries(
            arrayOf(
                 DataPoint(myDateList[0]!! + 0.0, myIntList[0]!!),
                 DataPoint(myDateList[1]!! + 0.0, myIntList[1]!!)
                 /*DataPoint(myDateList[2]!! + 0.0, myIntList[2]!!)*/
            )
        )
            if(graphMap[v.date] == null) {
                graphMap[v.date] = 0
            }else{
                var addme = graphMap[v.date]!! + v.cals
                graphMap[v.date] = addme
            }
        }

        var dataPts = arrayOfNulls<DataPoint>(graphMap.size)

        var iterator = 0
        for ((k,v) in graphMap){
            dataPts[iterator] = DataPoint(k.toDouble(), v.toDouble())
            iterator+=1
        }
        val series = LineGraphSeries<DataPoint>(dataPts)
        // Add series above to graph
        graph.addSeries(series);

        //Toggle display based on switch state
        switch.setOnClickListener {
            if(switch.isChecked){
                calsText.visibility = View.VISIBLE
                graph.visibility = View.INVISIBLE
            }
            else{
                calsText.visibility = View.INVISIBLE
                graph.visibility = View.VISIBLE
            }
        }
        return view
    }
}
