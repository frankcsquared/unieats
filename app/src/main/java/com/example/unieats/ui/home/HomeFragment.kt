package com.example.unieats.ui.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.unieats.MainActivity
import com.example.unieats.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var switch: Switch
    private var myDateList: MutableList<Int?> = mutableListOf<Int?>() // date
    private var myIntList: MutableList<Double?> = mutableListOf<Double?>() // calories

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        //initialize graph
        // val graph = view.findViewById(R.id.graph) as GraphView
        val graph = view.findViewById(R.id.chart) as LineChart

        val switch = view.findViewById(R.id.switch1) as Switch
        val calsText = view.findViewById(R.id.calsText) as TextView

        //Access Firebase
        val ref = FirebaseDatabase.getInstance().reference.child("Food")





        //inserts all things into map with date as index
        var graphMap = mutableMapOf<Int, Int>()
        var dateList = ArrayList<Int>()
        var num = 0
        var goal = MainActivity.selectedUser.goal


        fun refreshGraph(){
            val entries = ArrayList<Entry>()


            for ((k,v) in graphMap){
                Log.e(k.toString(), v.toString())
                entries.add(Entry(k.toFloat(), v.toFloat()))
            }



            // Add series above to graph
            val dataSet = LineDataSet(entries, "Calories") // add entries to dataset
            val lineData = LineData(dataSet)
            graph.setData(lineData);
            graph.invalidate(); // refresh

            //refresh toggle page
            calsText.setText(num.toString() + "/" + goal.toString())
        }


        for ((k, v) in MainActivity.selectedUser.history) {
            dateList.add(v.date)

            if(graphMap[v.date] == null) {
                graphMap[v.date] =  0
            }else{
                ref.addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        //get today's date
                        val current = LocalDateTime.now()

                        val formatter = DateTimeFormatter.BASIC_ISO_DATE
                        val formatted = current.format(formatter)
                        for (childSnapshot in dataSnapshot.children) {
                            if (childSnapshot.child("id").getValue(String::class.java).toString() == v.foodId){


                                var addme = graphMap[v.date]!! + childSnapshot.child("calories").getValue(Int::class.java)!!
                                if(v.date == formatted.toInt()) {
                                    num += childSnapshot.child("calories")
                                        .getValue(Int::class.java)!!
                                }

                                graphMap[v.date] = addme

                                refreshGraph()
                            }
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.e("Error:", "Failed to read value")
                    }
                })
            }
        }











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


