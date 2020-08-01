package com.example.unieats.ui.registration

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.unieats.MainActivity
import com.example.unieats.R
import com.example.unieats.databinding.FragmentNameBinding
import com.example.unieats.databinding.FragmentUserBinding
import com.example.unieats.models.History
import com.example.unieats.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class UserFragment : Fragment() {

    private val hint = "dwyncock6969"

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentUserBinding>(inflater,
            R.layout.fragment_user, container, false)

        val imgr =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        if (MainActivity.username == ""){
            binding.nameInput.hint = hint
        }
        else {
            binding.nameInput.setText(MainActivity.username)
        }

        binding.imageButton3.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_userFragment_to_lastNameFragment)
        }

        binding.imageButton4.setOnClickListener{
            MainActivity.username = binding.nameInput.text.toString()

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
                        view?.findNavController()?.navigate(R.id.action_userFragment_to_passFragment)
                        Log.e("a", MainActivity.username)
                    }else{
                        Toast.makeText(requireContext(), "Username already exists!", Toast.LENGTH_SHORT).show()
                    }

                    ref.removeEventListener(this);
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    //Toast.makeText(this@SearchFragment, "error error", Toast.LENGTH_LONG).show()
                }
            })






        }

        /*binding.nameInput.requestFocus()*/

        return binding.root
    }

    companion object {

    }
}