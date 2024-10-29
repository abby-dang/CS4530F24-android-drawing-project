package com.example.drawingapp

import android.graphics.Bitmap
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import com.example.drawingapp.databinding.FragmentMainScreenBinding

class MainScreenFragment : Fragment() {

    private val viewModel: SelectDrawingViewModel by viewModels{
        SelectDrawingViewModelFactory((requireActivity().application as DrawingApplication).drawingRepository)}

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

        binding.saveDrawingDemoBtn.setOnClickListener{
            Log.e("VM", "Button clicked")
            viewModel.saveDrawing(Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888), "WOW")
        }

        return binding.root
    }
}