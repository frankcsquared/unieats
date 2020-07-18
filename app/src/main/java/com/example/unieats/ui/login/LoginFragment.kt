package com.example.unieats.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.unieats.MainActivity
import com.example.unieats.R
import com.example.unieats.databinding.FragmentLoginBinding
import com.example.unieats.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_title.view.*
import kotlinx.android.synthetic.main.fragment_title.view.login_button


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        val ref = FirebaseDatabase.getInstance().reference.child("Users")

        var users = ArrayList<User>()

        val usernameText = root.findViewById<EditText>(R.id.usernameText)
        val passwordText = root.findViewById<EditText>(R.id.passwordText)
        val errorMsg = root.findViewById<TextView>(R.id.errorMsg)
        //generates array of users from database -> when logging in, queries for matching username, then checks pwd
        Log.e("ME HERE", "ASD")


        //get us the data
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (childSnapshot in dataSnapshot.children) {
                    val username = childSnapshot.child("username").getValue(String::class.java)
                    val password = childSnapshot.child("password").getValue(String::class.java)
                    if(username != null && password != null) {
                        users.add(User(username, password))
                    }
                }


            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                //Toast.makeText(this@SearchFragment, "error error", Toast.LENGTH_LONG).show()
            }
        })

        root.login_button.setOnClickListener {view: View->
            activity?.let{
                //Log.e("LOGIN USERS")
                var foundUser = false
                for (i in 0 until users.size){
                    Log.e("FOUND USER", users[i].username)
                    Log.e("FOUND pwd", users[i].password)
                    if(usernameText.text.toString() == users[i].username && passwordText.text.toString() == users[i].password){
                        Log.e("FOUND USER", users[i].username)
                        foundUser = true
                        errorMsg.visibility = View.INVISIBLE
                        val intent = Intent (it, MainActivity::class.java)
                        it.startActivity(intent)
                    }
                }

                if(foundUser == false){
                    errorMsg.visibility = View.VISIBLE
                }


            }
        }

        root.imageButton.setOnClickListener {view: View->
            view.findNavController().navigate(R.id.action_loginFragment_to_titleFragment)
        }




        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}