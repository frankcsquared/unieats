package com.example.unieats.ui.registration

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.unieats.MainActivity
import com.example.unieats.R
import com.example.unieats.databinding.FragmentLastnameBinding
import com.example.unieats.databinding.FragmentNameBinding


class LastNameFragment : Fragment() {

    private val hint = "Smith"

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLastnameBinding>(inflater,
            R.layout.fragment_lastname, container, false)

        val imgr =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        if (MainActivity.lastName == ""){
            binding.nameInput.hint = hint
        }
        else {
            binding.nameInput.setText(MainActivity.lastName)
        }

        binding.imageButton3.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_lastNameFragment_to_nameFragment)
        }

        binding.imageButton4.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_lastNameFragment_to_userFragment)
            MainActivity.lastName = binding.nameInput.text.toString()
        }

        /*binding.nameInput.requestFocus()*/

        return binding.root
    }

    companion object {

    }
}