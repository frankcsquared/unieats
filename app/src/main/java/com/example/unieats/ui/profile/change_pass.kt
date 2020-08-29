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

        val oldPass = root.findViewById<EditText>(R.id.oldPassword)
        val oldPassAgain = root.findViewById<EditText>(R.id.oldPasswordAgain)
        val newPass = root.findViewById<EditText>(R.id.newPassword)
        val newPassAgain = root.findViewById<EditText>(R.id.newPasswordAgain)

        root.changePasswordButton.setOnClickListener {
            if(oldPass.text.toString() == oldPassAgain.text.toString() && oldPass.text.toString() == MainActivity.selectedUser.password){
                if(newPass.text.toString() == newPassAgain.text.toString()){
                    ref.child("password").setValue(newPass.text.toString())
                }
            }


            Toast.makeText(requireContext(), "Password Changed", Toast.LENGTH_SHORT).show()

        }


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ChangePassViewModel::class.java)
        // TODO: Use the ViewModel
    }

}