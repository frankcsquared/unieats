package com.example.unieats.ui.registration

import android.content.Context
import android.net.http.SslCertificate.saveState
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.navigation.findNavController
import com.example.unieats.R
import com.example.unieats.databinding.FragmentNameBinding
import com.google.android.gms.common.config.GservicesValue.isInitialized
import kotlin.reflect.KProperty


class NameFragment : Fragment() {

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

        binding.nameInput.setText(model.getName())

        binding.imageButton3.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_nameFragment_to_titleFragment)
        }

        binding.imageButton4.setOnClickListener{
<<<<<<< Updated upstream
            view?.findNavController()?.navigate(R.id.action_nameFragment_to_emailFragment)
            model.setName(binding.nameInput.text.toString())
            Log.e("a", model.getName())
=======
            view?.findNavController()?.navigate(R.id.action_nameFragment_to_lastNameFragment)
>>>>>>> Stashed changes
        }

        /*binding.nameInput.requestFocus()*/

        return binding.root
    }

    companion object {

    }

}

