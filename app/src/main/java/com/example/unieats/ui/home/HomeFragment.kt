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

        //set graph labels
        graph.getViewport().setMinX(20200718.0);
        graph.getViewport().setMaxX(20200724.0);
        graph.getViewport().setMinY(200.0);
        graph.getViewport().setMaxY(10000.0);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);

        graph.getGridLabelRenderer().setHorizontalLabelsAngle(135);

        //var cals: Array<Double?> = myIntList.toTypedArray();

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
/*
    private fun displayData(): Array<DataPoint?>? {
        //val sdf = SimpleDateFormat("yyyy/MM/dd")
        //val currentDate = sdf.format(Date())
        val currentDate = 20200718
        val weekDate = currentDate + 7
        val count = 21
        var cnt = 1
        var x = 0.0
        var y = 0.0
        var currdate = 20200718
        val values = arrayOfNulls<DataPoint>(count)



        for ((k, v) in MainActivity.selectedUser.history){
            Log.e("HISTORY " + k, "VALUE")
            if(IntArray(weekDate){currentDate}.contains(MainActivity.selectedUser.history["$k"]!!.date)){
                x = MainActivity.selectedUser.history["$k"]!!.date!!.toDouble()
                if (currdate.toDouble() == x){
                    val v = DataPoint(20200718.0 + cnt/100, 2500.0 + cnt)
                    values[cnt] = v
                    y += MainActivity.selectedUser.history["$k"]!!.cals!!.toDouble()
                    cnt += 1
                }
                else{
                    Log.e("PRESENT", "PRESENT")
//                    val v =
//                        DataPoint(1.0, 3.0)
                        //DataPoint(currdate.toDouble(), y)
//                    values.add(v)
                    y = MainActivity.selectedUser.history["$k"]!!.cals!!.toDouble()
                    currdate = MainActivity.selectedUser.history["$k"]!!.date
                }
                //Log.e("DATE: " + currdate, "VALUE")
                //Log.e("CALS: " + y, "VALUE")
                Log.e("SIZE: " + values.size, "VALUE")
            }
        }
        return values
    }

 */

//    private fun generateData(): Array<DataPoint?>? {
//        Log.e("SIZE", "PRINT THIS")
//        val count = myDateList.size
//        val values =
//            arrayOfNulls<DataPoint>(count)
//        for (i in myDateList.indices) {
//            val x: Double = myDateList[i]!!.toDouble()
//            Log.e("PRINT " + x, "MESSAGE")
//            val y: Double = myIntList[i]!!.toDouble()
//            Log.e("PRINT " + y, "MESSAGE")
//            val v =
//                DataPoint(x, y)
//            values[i] = v
//        }
//        return values
//    }
}