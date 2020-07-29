package com.example.unieats.ui.registration

import android.content.Context
import com.example.unieats.R
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.unieats.databinding.FragmentEmailBinding
import com.example.unieats.databinding.FragmentHomeBinding
import com.example.unieats.databinding.FragmentNameBinding
import com.example.unieats.databinding.FragmentTitleBinding

class EmailFragment : Fragment() {

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val model: RegViewmodel by viewModels()

        val binding = DataBindingUtil.inflate<FragmentEmailBinding>(inflater,
            R.layout.fragment_email, container, false)

        val imgr =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        binding.emailInput.setText(model.getEmail())

        binding.imageButton3.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_emailFragment_to_nameFragment)
        }

        binding.imageButton4.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_emailFragment_to_uniFragment)
            model.setEmail(binding.emailInput.text.toString())
            Log.e("a", model.getEmail())
        }

        /*binding.emailInput.requestFocus()*/

        return binding.root
    }

    companion object {

    }
}