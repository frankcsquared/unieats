package com.example.unieats.ui.search

import android.R.attr.bitmap
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.unieats.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProviders.of(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        Log.e("IM HEERE", "FRAG HERE")
        val ref = FirebaseDatabase.getInstance().reference.child("Food")
        var myList: MutableList<String?> = mutableListOf<String?>() // title
        var myIntList: MutableList<Int?> = mutableListOf<Int?>() // calories
        var myBitList: MutableList<Bitmap?> = mutableListOf<Bitmap?>() // imageBitmap
        var foods: Array<String?> = myList.toTypedArray();
        var cals: Array<Int?> = myIntList.toTypedArray();
        var image: Array<Bitmap?> = myBitList.toTypedArray();
        // Read from the database
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (childSnapshot in dataSnapshot.children) {
                    Log.e("EEE", childSnapshot.child("name").getValue(String::class.java))
                    myList.add(childSnapshot.child("name").getValue(String::class.java))
                    Log.e("MYLIST", myList.size.toString())
                    foods = myList.toTypedArray()

                    myIntList.add(childSnapshot.child("calories").getValue(Int::class.java))
                    Log.e("CALS EEEE", childSnapshot.child("calories").getValue(Int::class.java).toString())
                    Log.e("CALS SIze: ", myIntList.size.toString())
                    cals = myIntList.toTypedArray()

                    var encodedImage = childSnapshot.child("image").getValue(String::class.java)

                    if(encodedImage != null) {
                        encodedImage = encodedImage.replace("data:image/jpeg;base64,","")
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
                Log.e("USERS", foods.size.toString())
                Log.e("EEE", "END OF LINE")

                val listView = root.findViewById<ListView>(R.id.foodList)
                val arrayAdapter: ArrayAdapter<*>
                val myListAdapter = MyListAdapter(requireActivity(),foods ,cals, image)
                listView.adapter = myListAdapter

/*
                // access the listView from xml file
                val mListView = root.findViewById<ListView>(R.id.foodList)
                arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_expandable_list_item_1, users)
                mListView!!.adapter = arrayAdapter

 */
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //Toast.makeText(this@SearchFragment, "error error", Toast.LENGTH_LONG).show()
            }
        })
    //geniusman https://stackoverflow.com/questions/44139841/how-to-retrieve-an-array-data-inside-an-array-from-firebase-database-android


        return root
    }


}

class Food(val id: String?, val name: String, val calories: Int, val gramsCarbs: Int, val gramsFats: Int,
           val gramsProteins: Int) {
    constructor() : this("","", 0, 0, 0, 0){

    }
}