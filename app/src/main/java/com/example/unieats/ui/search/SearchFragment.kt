package com.example.unieats.ui.search

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.PopupWindow
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.example.unieats.LogActivity.Companion.locationId
import com.example.unieats.MainActivity
import com.example.unieats.MapsActivity
import com.example.unieats.R
import com.example.unieats.databinding.FragmentSearchBinding
import com.example.unieats.models.Food
import com.example.unieats.models.History
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_checkout.*
import kotlinx.android.synthetic.main.fragment_search.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel

    companion object {
        var clickedFood = Food("",null,0)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSearchBinding>(inflater,
            R.layout.fragment_search, container, false)

        val loc = FirebaseDatabase.getInstance().reference.child("Location")

        // Read from the database
        loc.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.)
                for (childSnapshot in dataSnapshot.children) {
                    if (childSnapshot.child("id").getValue(String::class.java).toString()!! == "loc" + locationId.toString()) {
                        textView2.setText(childSnapshot.child("name").getValue(String::class.java).toString()!!)
                        //Log.e(" YES ", childSnapshot.child("name").getValue(String::class.java).toString()!!)
                        imageView2.setImageBitmap(toImage(childSnapshot.child("img").getValue(String::class.java).toString()!!))
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        binding.proceedButton.setOnClickListener {
            Log.e("yay","yay")
            activity?.let {
                val intent = Intent (it, MapsActivity::class.java)
                it.startActivity(intent)
            }
        }

        if (locationId != null) {
            Log.e("LOCATION", locationId.toString())
        }
        else {
            Log.e("RIP", "cock")
        }

        searchViewModel =
            ViewModelProviders.of(this).get(SearchViewModel::class.java)

        /*val root = inflater.inflate(R.layout.fragment_search, container, false)*/

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

                val listView = binding.foodList
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

        binding.fab.setOnClickListener{

            //INJECT LIST OF FOODS HERE
            //

            // inflate the layout of the popup window
            val inflater =
                context?.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView: View = inflater.inflate(R.layout.fragment_checkout, null)

            // create the popup window
            val wm =
                requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val size = Point()
            display.getSize(size)
            val width: Int = ((size.x) * 0.8).toInt()
            val height: Int = ((size.y) * 0.85).toInt()
            val focusable = true // lets taps outside the popup also dismiss it

            val popupWindow = PopupWindow(popupView, width, height, focusable)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.elevation = 20F;
            }

            // show the popup window
            // which view you pass in doesn't matter, it is only used for the window tolken
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

            /*popupView.setOnTouchListener { v, event ->
                popupWindow.dismiss()
                true
            }*/

            popupView.findViewById<Button>(R.id.confirmbtn).setOnClickListener{
                var ref = FirebaseDatabase.getInstance().getReference("Users/"+"${MainActivity.selectedUser.id}"+"/history")
                //this is where we will confirm logging like logAll();
                val current = LocalDateTime.now()

                val formatter = DateTimeFormatter.BASIC_ISO_DATE
                val formatted = current.format(formatter)

                for (i in MainActivity.cart) {
                    ref.push().setValue(History(formatted.toInt(), i.id)).addOnCompleteListener{
                        MainActivity.cart.remove(i)
                    }
                }

                popupWindow.dismiss()

                activity?.finish()
                activity?.let {
                    val intent = Intent (it, MainActivity::class.java)
                    it.startActivity(intent)
                }

            }

        }

        return binding.root
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


