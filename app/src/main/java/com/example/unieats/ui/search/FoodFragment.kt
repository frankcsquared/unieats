package com.example.unieats.ui.search

import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.unieats.MainActivity
import com.example.unieats.R
import com.example.unieats.models.History
import com.example.unieats.models.User
import com.example.unieats.ui.search.SearchFragment.Companion.clickedFood
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.food_fragment.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class FoodFragment : Fragment() {

    companion object {
        fun newInstance() = FoodFragment()
    }

    private lateinit var viewModel: FoodViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.food_fragment, container, false)
        var ref = FirebaseDatabase.getInstance().getReference("Users/"+"${MainActivity.selectedUser.id}"+"/history").push()
        var readRef = FirebaseDatabase.getInstance().getReference("Users/"+"${MainActivity.selectedUser.id}"+"/history")

        var title = root.findViewById<TextView>(R.id.foodTitle)
        var cals = root.findViewById<TextView>(R.id.foodCals)
        var img = root.findViewById<ImageView>(R.id.foodImage)

        var total = root.findViewById<TextView>(R.id.todayTotal)


        var totalCnt = 0

        title.text = clickedFood.name
        cals.text = clickedFood.calories.toString()
        img.setImageBitmap(clickedFood.image)

        //val logButton = root.findViewById<Button>(R.id.log_button)

        root.foodButton.setOnClickListener { view: View ->
            val current = LocalDateTime.now()

            val formatter = DateTimeFormatter.BASIC_ISO_DATE
            val formatted = current.format(formatter)
            Log.e(formatted, formatted)
            ref.setValue(History(formatted.toInt(),clickedFood.calories,10)).addOnCompleteListener{
                Toast.makeText(requireContext(), "Food logged successfully", Toast.LENGTH_LONG).show()
            }
        }

        root.removeButton.setOnClickListener { view: View ->
            val current = LocalDateTime.now()

            val formatter = DateTimeFormatter.BASIC_ISO_DATE
            val formatted = current.format(formatter)
            Log.e(formatted, formatted)
            ref.setValue(History(formatted.toInt(),-1*clickedFood.calories,10)).addOnCompleteListener{
                Toast.makeText(requireContext(), "Food removed successfully", Toast.LENGTH_LONG).show()
            }


        }

        readRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (childSnapshot in dataSnapshot.children) {

                    val addition = childSnapshot.child("cals").getValue(Long::class.java)!!
                    totalCnt += addition.toInt()
                    total.text = "Today's Total: " + totalCnt
                }
            }


            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //Toast.makeText(this@SearchFragment, "error error", Toast.LENGTH_LONG).show()
            }
        })


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FoodViewModel::class.java)
        // TODO: Use the ViewModel
    }

}