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
import com.example.unieats.MainActivity
import com.example.unieats.MainActivity.Companion.locationId
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
        var myIntList: MutableList<Int?> = mutableListOf<Int?>() // calories
        var myBitList: MutableList<Bitmap?> = mutableListOf<Bitmap?>() // imageBitmap
        var foods: Array<String?> = myList.toTypedArray();
        var cals: Array<Int?> = myIntList.toTypedArray();
        var image: Array<Bitmap?> = myBitList.toTypedArray();

        var encodedImage : String? = ""

        // Read from the database
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (childSnapshot in dataSnapshot.children) {
                    //childSnapshot.child("name").getValue(String::class.java)
                    myList.add(childSnapshot.child("name").getValue(String::class.java))

                    foods = myList.toTypedArray()

                    myIntList.add(childSnapshot.child("calories").getValue(Int::class.java))

                    cals = myIntList.toTypedArray()

                    encodedImage = childSnapshot.child("image").getValue(String::class.java)

                    if(encodedImage != null) {
                        encodedImage = encodedImage!!.replace("data:image/jpeg;base64,","")
                        val decodedString: ByteArray =
                            Base64.decode(encodedImage, Base64.DEFAULT)
                        val decodedByte =
                            BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

                        myBitList.add(decodedByte)
                        //Log.e("IMG STR EEEE", childSnapshot.child("calories").getValue(Bitmap::class.java).toString())
                        //Log.e("CALS SIze: ", myIntList.size.toString())
                        image = myBitList.toTypedArray()
                        //im.setImageBitmap(decodedByte);
                       // Log.e("STRING LONG", encodedImage)
                       // val im: Drawable = BitmapDrawable(resources, decodedByte)



                    }else{
                        myBitList.add(null)
                        //Log.e("IMG STR EEEE", childSnapshot.child("calories").getValue(Bitmap::class.java).toString())
                        //Log.e("CALS SIze: ", myIntList.size.toString())
                        image = myBitList.toTypedArray()
                    }

                    //Log.e("asd","asdf")

                }

                val listView = root.findViewById<ListView>(R.id.foodList)
                val arrayAdapter: ArrayAdapter<*>
                val myListAdapter = MyListAdapter(requireActivity(),foods ,cals, image)
                listView.adapter = myListAdapter




                listView.setOnItemClickListener { adapter, v, position, arg3 ->
                    if(foods[position] != null && cals[position] != null ) {
                        if(image[position] != null){
                            clickedFood = Food(foods[position].toString(),image[position], cals[position]!!)
                        }else {
                            clickedFood = Food(foods[position].toString(), null, cals[position]!!)
                        }
                    }

                    //val test = Food(foods[position].toString(), "", cals[position]!!)
                    //Log.e("TEST", test.name)
                    listView.findNavController().navigate(R.id.action_navigation_search_to_foodFragment)
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


