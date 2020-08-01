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
import com.example.unieats.MainActivity
import com.example.unieats.databinding.FragmentHomeBinding
import com.example.unieats.databinding.FragmentNameBinding
import com.example.unieats.databinding.FragmentTitleBinding
import com.example.unieats.databinding.FragmentUniBinding

class UniFragment : Fragment() {

    private val hint = "mcdicks uni"

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
            view?.findNavController()?.navigate(R.id.action_uniFragment_to_finalProfileFragment)
            MainActivity.uni = binding.uniInput.text.toString()
            Log.e("a", MainActivity.uni)
        }

        /*binding.uniInput.requestFocus()*/

        return binding.root
    }

    companion object {

    }
}