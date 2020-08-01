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
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.util.*


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
<<<<<<< HEAD
        val graph = view.findViewById(R.id.chart) as LineChart
        //graph.addSeries(mSeries1)
=======
        // val graph = view.findViewById(R.id.graph) as GraphView
        val graph = view.findViewById(R.id.chart) as LineChart

>>>>>>> 217673f8abac91ffc1bcd0eb0fbef8ba10ac2fd6
        val switch = view.findViewById(R.id.switch1) as Switch
        val calsText = view.findViewById(R.id.calsText) as TextView

        //Access Firebase
        val ref = FirebaseDatabase.getInstance().reference.child("Food")

        //set graph labels
//        graph.getViewport().setMinX(20200718.0);
//        graph.getViewport().setMaxX(20200722.0);
//        graph.getViewport().setMinY(200.0);
//        graph.getViewport().setMaxY(5000.0);
//
//        graph.getViewport().setYAxisBoundsManual(true);
//        graph.getViewport().setXAxisBoundsManual(true);
//
//        graph.getGridLabelRenderer().setHorizontalLabelsAngle(135);

//        val nf: NumberFormat = NumberFormat.getInstance()
//        nf.setMinimumFractionDigits(3)
//        nf.setMinimumIntegerDigits(2)

//        graph.gridLabelRenderer.labelFormatter = DefaultLabelFormatter(nf, nf)
//        graph.getGridLabelRenderer().setNumHorizontalLabels(2); // only 3 because of the space
//        graph.getGridLabelRenderer().setNumVerticalLabels(2); // only 3 because of the space


        Log.e("LENGTH " +  MainActivity.selectedUser.history.size, " SIZE")

        //inserts all things into map with date as index
        var graphMap = mutableMapOf<Int, Int>()
        var num = 0
        var goal = MainActivity.selectedUser.goal

        for ((k, v) in MainActivity.selectedUser.history) {
//            var d = Date(v.date.toLong())
//            val format = SimpleDateFormat("yyyyMMdd")
//            val date = format.format(d)

            if(graphMap[v.date] == null) {
                graphMap[v.date] =  0
            }else{
                ref.addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (childSnapshot in dataSnapshot.children) {
                            if (childSnapshot.child("id").getValue(String::class.java).toString() == v.foodId){
                                var addme = graphMap[v.date]!! + childSnapshot.child("calories").getValue(Int::class.java)!!
                                num += childSnapshot.child("calories").getValue(Int::class.java)!!
                                graphMap[v.date] = addme
                            }
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.e("Error:", "Failed to read value")
                    }
                })
            }
        }

        val entries = ArrayList<Entry>()

        //var iterator = 0
        for ((k,v) in graphMap){
//            dataPts[iterator] = DataPoint(k.toDouble(), v.toDouble())
            entries.add(Entry(k.toFloat(), v.toFloat()))
//            iterator+=1
        }
        //val series = LineGraphSeries<DataPoint>(dataPts)
        // Add series above to graph
        val dataSet = LineDataSet(entries, "Label") // add entries to dataset
        val lineData = LineData(dataSet)
        graph.setData(lineData);
        graph.invalidate(); // refresh

        calsText.setText(num.toString() + "/" + goal.toString())

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
