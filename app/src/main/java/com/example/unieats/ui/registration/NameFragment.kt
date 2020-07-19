package com.example.unieats.ui.registration

import com.example.unieats.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.unieats.databinding.FragmentHomeBinding
import com.example.unieats.databinding.FragmentNameBinding
import com.example.unieats.databinding.FragmentTitleBinding

class NameFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentNameBinding>(inflater,
            R.layout.fragment_name, container, false)

        return binding.root
    }

    companion object {

    }
}