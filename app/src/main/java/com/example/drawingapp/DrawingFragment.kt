package com.example.drawingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.drawingapp.databinding.FragmentDrawingBinding


/**
 * The drawing fragment. Holds the canvas and drawing tools.
 */
class DrawingFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: DrawingViewModel by viewModels()

        val binding = FragmentDrawingBinding.inflate(layoutInflater, container, false)
        // Create a CanvasView with the view model in it.
        val canvasView = CanvasView(requireContext(), null, viewModel)
        binding.canvasHolderID.addView(canvasView)

        //setting navigation for back button
        binding.backButtonID.setOnClickListener{
            findNavController().navigate(R.id.action_back)
        }
        return binding.root
    }
}