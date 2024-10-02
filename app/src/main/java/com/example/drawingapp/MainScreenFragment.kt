package com.example.drawingapp

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.drawingapp.databinding.FragmentMainScreenBinding

class MainScreenFragment : Fragment() {

    private val viewModel: MainScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainScreenBinding.inflate(layoutInflater, container, false)

        //setting up navigation for opening drawing screen MAY CHANGE LATER
        binding.openDrawingBtn.setOnClickListener{
            findNavController().navigate(R.id.action_open_drawing)
        }

        //setting up navigation for opening cloud screen MAY CHANGE LATER
        binding.openCloudBtn.setOnClickListener{
            findNavController().navigate(R.id.action_open_cloud)
        }
        return binding.root
    }
}