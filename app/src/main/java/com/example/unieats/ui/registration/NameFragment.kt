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
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.unieats.MainActivity
import com.example.unieats.R
import com.example.unieats.databinding.FragmentNameBinding


class NameFragment : Fragment() {

    private val hint = "John"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val model: RegViewmodel by viewModels()

        val binding = DataBindingUtil.inflate<FragmentNameBinding>(inflater,
            R.layout.fragment_name, container, false)

        val imgr =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        if (MainActivity.firstName == ""){
            binding.nameInput.hint = hint
        }
        else {
            binding.nameInput.setText(MainActivity.firstName)
        }

        binding.imageButton3.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_nameFragment_to_titleFragment)
            MainActivity.reset()
        }

        binding.imageButton4.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_nameFragment_to_lastNameFragment)
            MainActivity.firstName = binding.nameInput.text.toString()
        }

        /*binding.nameInput.requestFocus()*/

        return binding.root
    }

    companion object {

    }

    interface FragmentToActivity {
        fun communicate(comm: String?)
    }

}

