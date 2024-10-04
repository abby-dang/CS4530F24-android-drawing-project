package com.example.drawingapp

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.drawingapp.databinding.FragmentCloudBinding

class CloudFragment : Fragment() {

    private val viewModel: CloudViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCloudBinding.inflate(layoutInflater, container, false)

        //setting navigation on back button
        binding.backBtn.setOnClickListener{
            findNavController().navigate(R.id.action_back)
        }
        return binding.root
    }
}