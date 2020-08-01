package com.example.unieats.ui.search

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.unieats.LogActivity.Companion.locationId
import com.example.unieats.MainActivity
import com.example.unieats.R
import com.example.unieats.models.Food
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.math.log


class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel

    companion object {
        var clickedFood = Food("",null,0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (locationId != null) {
            Log.e("LOCATION", locationId.toString())
        }
        else {
            Log.e("RIP", "cock")
        }

        searchViewModel =
            ViewModelProviders.of(this).get(SearchViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_search, container, false)

        val ref = FirebaseDatabase.getInstance().reference.child("Food")
        var myList: MutableList<String?> = mutableListOf<String?>() // title

        var foods: Array<String?> = myList.toTypedArray();


        var foodList: MutableList<Food> = mutableListOf<Food>()



        // Read from the database
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (childSnapshot in dataSnapshot.children) {
                    //filter by location
                    if (childSnapshot.child("locationid").getValue(String::class.java).toString()!! == "loc" + locationId.toString()) {
                        foodList.add(childSnapshot.getValue(Food::class.java)!!)
                    }

                }

                val listView = root.findViewById<ListView>(R.id.foodList)
               //makes title array for arrayAdapter inherit in MyListAdapter
                val titleArr = arrayListOf<String?>();
                for (i in foodList){
                    titleArr.add(i.name)
                }
                val title = titleArr.toTypedArray()

                //injects Food to List Object
                val myListAdapter = MyListAdapter(requireActivity(), title, foodList.toTypedArray())
                listView.adapter = myListAdapter

                listView.setOnItemClickListener { adapter, v, position, arg3 ->
                    clickedFood = foodList[position]

                    listView.findNavController().navigate(R.id.action_searchFragment_to_foodFragment)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //Toast.makeText(this@SearchFragment, "error error", Toast.LENGTH_LONG).show()
            }
        })


        return root
    }


}


fun toImage(inString: String) : Bitmap?{
    val encodedImage: String;
    return if(inString != null) {
        encodedImage = inString!!.replace("data:image/jpeg;base64,","")
        val decodedString: ByteArray = Base64.decode(encodedImage, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

        decodedByte
    }else{
        null
    }
}




