package com.example.drawingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels



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
    ): View? {
        val viewModel: DrawingViewModel by viewModels()
        val root = inflater.inflate(R.layout.fragment_drawing, container, false)

        // Create a CanvasView with the view model in it.
        val canvasView = CanvasView(requireContext(), null, viewModel)
        val layout = root.findViewById<FrameLayout>(R.id.canvasHolderID)
        layout.addView(canvasView)

        return root
    }
}