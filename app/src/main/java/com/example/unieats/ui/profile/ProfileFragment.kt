package com.example.unieats.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.unieats.MainActivity
import com.example.unieats.MainActivity.Companion.selectedUser
import com.example.unieats.R
import com.example.unieats.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val profileName = root.findViewById<TextView>(R.id.profileName) //shown top
        val editFirst = root.findViewById<EditText>(R.id.editFirstName)
        val editLast = root.findViewById<EditText>(R.id.editLastName)
        val editGoal = root.findViewById<EditText>(R.id.editTodayGoal)

        val editEmail = root.findViewById<EditText>(R.id.editEmail)
        val editUni = root.findViewById<EditText>(R.id.editUni)

        val ref = FirebaseDatabase.getInstance().reference.child("Users/${MainActivity.selectedUser.id}")



        root.updateInfoButton.setOnClickListener {
            ref.child("first_name").setValue(editFirst.text.toString())
            ref.child("last_name").setValue(editLast.text.toString())
            ref.child("goal").setValue(editGoal.text.toString())

            ref.child("email").setValue(editEmail.text.toString())
            ref.child("uni").setValue(editUni.text.toString())

            Toast.makeText(requireContext(), "Updated!", Toast.LENGTH_SHORT).show()



        }
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                MainActivity.selectedUser = dataSnapshot.getValue(User::class.java)!!
                profileName.text = selectedUser.first_name + " "  + selectedUser.last_name
                editFirst.setText(selectedUser.first_name)
                editLast.setText(selectedUser.last_name)
                editEmail.setText(selectedUser.email)
                editUni.setText(selectedUser.uni)



                editGoal.setText(selectedUser.goal.toString())


            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //Toast.makeText(this@SearchFragment, "error error", Toast.LENGTH_LONG).show()
            }
        })

        return root
    }
}