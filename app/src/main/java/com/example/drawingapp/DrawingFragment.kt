package com.example.drawingapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.GridView
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.drawingapp.databinding.FragmentDrawingBinding


/**
 * The drawing fragment. Holds the canvas and drawing tools.
 */
class DrawingFragment : Fragment() {

    private val myViewModel : DrawingViewModel by viewModels()

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

        // Create a CanvasView with the view model in it
        val canvasView = CanvasView(requireContext(), null, viewModel)
        binding.canvasHolderID.addView(canvasView)

        // Setting navigation for back button
        binding.backButtonID.setOnClickListener{
            findNavController().navigate(R.id.action_back)
        }

        // Set tool bar buttons
        binding.brushButtonID.setOnClickListener{
            myViewModel.setBrush()
        }
        binding.eraserButtonID.setOnClickListener{
            myViewModel.setEraser()
        }
        binding.sizeBarID.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                // Change size display
                override fun onProgressChanged(seek: SeekBar,
                                               progress: Int, fromUser: Boolean) {
                    binding.sizeDisplayID.text = seek.progress.toString() + "%"
                }
                override fun onStartTrackingTouch(seek: SeekBar) { }

                // Change brush/eraser size
                override fun onStopTrackingTouch(seek: SeekBar) {
                    myViewModel.setSize(seek.progress.toFloat())
                }
            })

        // Set color grid
        val colorList = listOf(Color.BLACK, Color.LTGRAY, Color.GRAY, Color.DKGRAY, Color.WHITE, Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA)
        val gridView = binding.colorGridView

        val adapter = ColorGridAdapter(this.context, colorList)
        gridView.adapter = adapter

        gridView.setOnItemClickListener { _, _, position, _ ->
            myViewModel.setColor(colorList[position])
        }

        return binding.root
    }
}