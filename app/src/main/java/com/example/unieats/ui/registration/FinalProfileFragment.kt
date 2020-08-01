package com.example.unieats.ui.registration

import com.example.unieats.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.unieats.databinding.FragmentFinalprofileBinding

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

        binding.imageButton3.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_finalProfileFragment_to_uniFragment)
        }

        binding.toProfile.setOnClickListener{

            /*
           use MainActivity.field (.firstName, ,.lastName, etc)
           to access all the things. reset() at the end.
            */
            
        }

        return binding.root
    }

    companion object {

    }
}