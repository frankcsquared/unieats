package com.example.unieats.ui.profile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.unieats.MainActivity
import com.example.unieats.R
import com.example.unieats.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.change_pass_fragment.view.*

class change_pass : Fragment() {

    companion object {
        fun newInstance() = change_pass()
    }

    private lateinit var viewModel: ChangePassViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.change_pass_fragment, container, false)

        val ref = FirebaseDatabase.getInstance().reference.child("Users/${MainActivity.selectedUser.id}")

        val editPass = root.findViewById<EditText>(R.id.oldPassword)

        root.changePasswordButton.setOnClickListener {
            ref.child("password").setValue(editPass.text.toString())

            Toast.makeText(requireContext(), "Password Changed", Toast.LENGTH_SHORT).show()

        }

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                MainActivity.selectedUser = dataSnapshot.getValue(User::class.java)!!

                editPass.setText(MainActivity.selectedUser.password)



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
        viewModel = ViewModelProviders.of(this).get(ChangePassViewModel::class.java)
        // TODO: Use the ViewModel
    }

}