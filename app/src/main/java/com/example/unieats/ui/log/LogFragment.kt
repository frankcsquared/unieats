package com.example.unieats.ui.log

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.unieats.MapsActivity
import com.example.unieats.R
import com.example.unieats.databinding.FragmentLogBinding

class LogFragment : Fragment() {

    private lateinit var logViewModel: LogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLogBinding>(
            inflater,
            R.layout.fragment_log, container, false
        )

        binding.log_button.setonClickListener {
            activity?.let{
                val intent = Intent (it, MapsActivity::class.java)
                it.startActivity(intent)
            }
        }
        //logViewModel =
            //ViewModelProviders.of(this).get(LogViewModel::class.java)
        //val root = inflater.inflate(R.layout.fragment_log, container, false)

        return binding.root
    }
}