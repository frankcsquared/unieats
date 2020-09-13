package com.example.unieats.ui.home

import android.graphics.Color
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
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.EntryXComparator
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
        val graph = view.findViewById(R.id.chart) as BarChart
        graph.description.isEnabled = false;

        val pieChart = view.findViewById(R.id.pieChart) as PieChart
        pieChart.description.isEnabled = false;
        val switch = view.findViewById(R.id.switch1) as Switch

        val graphTitle = view.findViewById(R.id.graphTitle) as TextView
        val calsText = view.findViewById(R.id.calsText) as TextView

        //Access Firebase
        val ref = FirebaseDatabase.getInstance().reference.child("Food")





        //inserts all things into map with date as index
        var graphMap = mutableMapOf<Int, Int>()
        var dateList = ArrayList<Int>()
        var num = 0
        var goal = MainActivity.selectedUser.goal

        var pieMap = mutableMapOf<String, Int>()
        //initialize 3 macros cuz theres only 3
        pieMap["Carbs"] = 0
        pieMap["Proteins"] = 0
        pieMap["Fats"] = 0

        fun refreshGraph(){
            //colors for graph
            val MY_COLORS = intArrayOf(
                Color.rgb(192, 0, 0),
                Color.rgb(255, 0, 0),
                Color.rgb(255, 192, 0),
                Color.rgb(127, 127, 127),
                Color.rgb(146, 208, 80),
                Color.rgb(0, 176, 80),
                Color.rgb(79, 129, 189)
            )

            val entries = ArrayList<BarEntry>()
            val pieEntries = ArrayList<PieEntry>()

            //get current date, used to find current month
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.BASIC_ISO_DATE
            val isoDate = current.format(formatter).toInt()
            val curMonth = (isoDate-20200000)-(isoDate-20200000)%100

            var largestDate = 1

            // loops through database here
            for ((k,v) in graphMap){
                val entryMonth = (k-20200000) - (k-20200000) % 100
                val entryDay = (k-20200000) % 100
                //only accept entries in the current month
                if(entryMonth == curMonth) {

                    entries.add(BarEntry(entryDay.toFloat(), v.toFloat()))
                    //get largest date (for 0s below)
                    if(entryDay > largestDate){
                        largestDate = entryDay
                    }
                }

            }

            for ((k,v) in pieMap) {
                pieEntries.add(PieEntry(v.toFloat(), k))
            }

            //create 0s here to inject into entries
            //largestDate ALWAYS has entry (as its from entries)
            for (i in 0 until largestDate){
                var xExists = false
                for (j in entries){
                    if(j.x == i.toFloat()){
                        xExists = true
                    }
                }
                if(xExists == false){
                    entries.add(BarEntry(i.toFloat(), 0.toFloat()))
                }
            }

            //sort then,
            Collections.sort(entries, EntryXComparator())

            //define colors for graph (based on MY_COLORS AT TOP of func)
            val colors = ArrayList<Int>()
            for (c in MY_COLORS) colors.add(c)

            // Add series above to graph
            val dataSet = BarDataSet(entries.toMutableList(), "Calories") // add entries to dataset
            dataSet.setColor(getResources().getColor(R.color.darker_red))
            val lineData = BarData(dataSet)
            graph.data = lineData;
            //Piechart data add

            val pieDataSet = PieDataSet(pieEntries.toMutableList(), "")
            pieDataSet.colors = colors
            val pieData = PieData(pieDataSet)

            pieChart.data = pieData


            //graph.setVisibleXRange(20200801.toFloat(), 20200820.toFloat())
            graph.invalidate(); // refresh
            pieChart.invalidate();
            //refresh toggle page
            calsText.text = num.toString() + "/" + goal.toString()
        }


        for ((k, v) in MainActivity.selectedUser.history) {

            dateList.add(v.date)
            //populate graphMap

            if(graphMap[v.date] == null) {
                graphMap[v.date] =  0
            }
            ref.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    //get today's date
                    val current = LocalDateTime.now()
                    val incr = current.plusDays(40)
                    //val diff
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
                            if(v.date == formatted.toInt()) {

                                pieMap["Carbs"] =
                                    pieMap["Carbs"]!! + childSnapshot.child("gramsCarbs")
                                        .getValue(Int::class.java)!!
                                pieMap["Proteins"] =
                                    pieMap["Proteins"]!! + childSnapshot.child("gramsProteins")
                                        .getValue(Int::class.java)!!
                                pieMap["Fats"] = pieMap["Fats"]!! + childSnapshot.child("gramsFats")
                                    .getValue(Int::class.java)!!
                            }
                            refreshGraph()
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })


        }













        //Toggle display based on switch state
        switch.setOnClickListener {
            if(switch.isChecked){
                pieChart.visibility = View.VISIBLE
                graph.visibility = View.INVISIBLE
                graphTitle.text = "Daily Macros"
            }
            else{
                pieChart.visibility = View.INVISIBLE
                graph.visibility = View.VISIBLE
                graphTitle.text = "This Month's Progress"
            }
        }
        return view
    }


}


