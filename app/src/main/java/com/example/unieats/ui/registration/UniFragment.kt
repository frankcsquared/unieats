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
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.unieats.MainActivity
import com.example.unieats.databinding.FragmentHomeBinding
import com.example.unieats.databinding.FragmentNameBinding
import com.example.unieats.databinding.FragmentTitleBinding
import com.example.unieats.databinding.FragmentUniBinding
import com.example.unieats.models.History
import com.example.unieats.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UniFragment : Fragment() {

    private val hint = "McMaster University"

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val model: RegViewmodel by viewModels()

        val binding = DataBindingUtil.inflate<FragmentUniBinding>(inflater,
            R.layout.fragment_uni, container, false)

        val imgr =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        if (MainActivity.uni == ""){
            binding.uniInput.hint = hint
        }
        else {
            binding.uniInput.setText(MainActivity.uni)
        }

        binding.imageButton3.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_uniFragment_to_emailFragment)
        }

        binding.imageButton4.setOnClickListener{
/*            view?.findNavController()?.navigate(R.id.action_uniFragment_to_finalProfileFragment)*/
            MainActivity.uni = binding.uniInput.text.toString()

            //database ref
            val ref = FirebaseDatabase.getInstance().getReference("Users")

            var noMatch = true
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for  (childSnapshot in dataSnapshot.children){

                        if(childSnapshot.child("username").getValue(String::class.java) == MainActivity.username){
                            noMatch = false
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

                        //LOGIN
                        activity?.let{
                            MainActivity.selectedUser = userPush
                            val intent = Intent (it, MainActivity::class.java)
                            it.startActivity(intent)

                        }
                    }
                    ref.removeEventListener(this);

                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    //Toast.makeText(this@SearchFragment, "error error", Toast.LENGTH_LONG).show()
                }
            })
        }

        /*binding.uniInput.requestFocus()*/

        return binding.root
    }

    companion object {

    }
}