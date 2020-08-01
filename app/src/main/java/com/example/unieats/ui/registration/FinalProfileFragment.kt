package com.example.unieats.ui.registration

import android.content.Context
import android.content.Intent
import com.example.unieats.R
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.unieats.MainActivity
import com.example.unieats.ContextExtensions.hideKeyboard
import com.example.unieats.LogActivity
import com.example.unieats.databinding.FragmentFinalprofileBinding
import com.example.unieats.models.History
import com.example.unieats.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FinalProfileFragment : Fragment() {

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentFinalprofileBinding>(inflater,
            R.layout.fragment_finalprofile, container, false)

        val imgr =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imgr.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        hideKeyboard()

        binding.imageButton3.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_finalProfileFragment_to_uniFragment)
        }

        binding.toProfile.setOnClickListener{
            //database ref
            val ref = FirebaseDatabase.getInstance().getReference("Users")

            var noMatch = true
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for  (childSnapshot in dataSnapshot.children){

                        if(childSnapshot.child("username").getValue(String::class.java) == MainActivity.username){
                            noMatch = false
                            Log.e("FAIL", "user match")
                            break;
                        }

                    }
                    if(noMatch){
                        val pusher = ref.push()
                        val id = pusher.key

                        val histor: Map<String, History> =mutableMapOf<String, History>()
                        val userPush = User(id!!,MainActivity.firstName, MainActivity.lastName, histor ,MainActivity.username, MainActivity.password, "0", MainActivity.email, MainActivity.uni)
                        //val userPush = User("","","",histor,"","","","","")
                        pusher.setValue(userPush).addOnCompleteListener{
                            Toast.makeText(requireContext(), "Registered!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    ref.removeEventListener(this);
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    //Toast.makeText(this@SearchFragment, "error error", Toast.LENGTH_LONG).show()
                }
            })
            /*
           use MainActivity.field (.firstName, ,.lastName, etc)
           to access all the things. reset() at the end.
            */
            activity?.let {
                val intent = Intent (it, MainActivity::class.java)
                it.startActivity(intent)
            }
        }

        return binding.root
    }

    companion object {

    }
}