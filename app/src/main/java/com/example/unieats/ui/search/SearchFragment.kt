package com.example.unieats.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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
        var myList: MutableList<String?> = mutableListOf<String?>()
        var users: Array<String?> = myList.toTypedArray();

        // Read from the database
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                /*
                Log.e("FBASEEEE", "ONDATA FUNC")
                val value =
                    dataSnapshot.getValue(Food::class.java)
                if (value != null) {
                    Log.e("FBASEEEE", value.javaClass.name)

                    //Toast.makeText(this, value.name, Toast.LENGTH_LONG).show()
                }

                 */

                for (childSnapshot in dataSnapshot.children) {
                    Log.e("EEE", childSnapshot.child("name").getValue(String::class.java))
                    myList.add(childSnapshot.child("name").getValue(String::class.java))
                    Log.e("MYLIST", myList.size.toString())
                    users = myList.toTypedArray()
                }
                Log.e("USERS", users.size.toString())
                Log.e("EEE", "END OF LINE")

                val arrayAdapter: ArrayAdapter<*>

                // access the listView from xml file
                val mListView = root.findViewById<ListView>(R.id.userlist)
                arrayAdapter = ArrayAdapter(requireContext(),
                    android.R.layout.simple_list_item_1, users)
                mListView!!.adapter = arrayAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //Toast.makeText(this@SearchFragment, "error error", Toast.LENGTH_LONG).show()
            }
        })
    //geniusman https://stackoverflow.com/questions/44139841/how-to-retrieve-an-array-data-inside-an-array-from-firebase-database-android

        //LISTVIEW SHOWING STUFF

        // use arrayadapter and define an array6
        val arrayAdapter: ArrayAdapter<*>
        /*val users = arrayOf(
            "abc", "bob dylan"
        )

         */


        //Log.e("CHECK MYLIST", users.size.toString())
        //Log.e("CHECK MYLIST", users[0])
        // access the listView from xml file
        val mListView = root.findViewById<ListView>(R.id.userlist)
        arrayAdapter = ArrayAdapter(this.requireContext(),
            android.R.layout.simple_list_item_1, users)
        mListView!!.adapter = arrayAdapter




        return root
    }


}

class Food(val id: String?, val name: String, val calories: Int, val gramsCarbs: Int, val gramsFats: Int,
           val gramsProteins: Int) {
    constructor() : this("","", 0, 0, 0, 0){

    }
}