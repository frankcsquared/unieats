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
import com.example.unieats.databinding.FragmentNameBinding
import com.example.unieats.databinding.FragmentPassBinding


class PassFragment : Fragment() {

    private val hint = "cockerjohn"

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentPassBinding>(inflater,
            R.layout.fragment_pass, container, false)

        val imgr =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        if (MainActivity.password == ""){
            binding.nameInput.hint = hint
        }
        else {
            binding.nameInput.setText(MainActivity.password)
        }

        binding.imageButton3.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_passFragment_to_userFragment)
        }

        binding.imageButton4.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_passFragment_to_emailFragment)
            MainActivity.password = binding.nameInput.text.toString()
            Log.e("a", MainActivity.password)
        }

        /*binding.nameInput.requestFocus()*/

        return binding.root
    }

    companion object {

    }
}