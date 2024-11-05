package com.example.drawingapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
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
        val converter = BitmapConverter()
        val bitmap = converter.toBitmap(arguments?.getByteArray("bitmap"))

        val viewModel: DrawingViewModel by viewModels()
        if (bitmap != null) {
            viewModel.updateBitmap(bitmap)
        }
        else {
            viewModel.updateBitmap(Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888))
        }

        val binding = FragmentDrawingBinding.inflate(layoutInflater, container, false)

        // Create a CanvasView with the view model in it
        val canvasView = CanvasView(requireContext(), null, viewModel)
        binding.canvasHolderID.addView(canvasView)

        // Setting navigation for back button
        binding.backButtonID.setOnClickListener{
            findNavController().navigate(R.id.action_back)
        }

        // Set tool bar buttons
        binding.circleBrushButtonID.setOnClickListener{
            myViewModel.setCircleBrush()
        }
        binding.squareBrushButtonID.setOnClickListener{
            myViewModel.setSquareBrush()
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
        val colorList = listOf(Color.BLACK, Color.DKGRAY, Color.LTGRAY, Color.WHITE, Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA)
        val gridView = binding.colorGridView

        val adapter = ColorGridAdapter(this.context, colorList)
        gridView.adapter = adapter

        gridView.setOnItemClickListener { _, _, position, _ ->
            var selectedColor = colorList[position]
            myViewModel.setColor(selectedColor)
            binding.currentColorDisplay.background = ColorDrawable(selectedColor)
        }

        return binding.root
    }
}