package com.example.drawingapp

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
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
    private val selectDrawingViewModel: SelectDrawingViewModel by viewModels{
        SelectDrawingViewModelFactory((requireActivity().application as DrawingApplication).drawingRepository)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val converter = BitmapConverter()
        val bitmap = converter.toBitmap(arguments?.getByteArray("bitmap"))

        if (bitmap != null) {
            Log.d("Load Canvas", "Load Canvas")
            val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
            myViewModel.updateBitmap(mutableBitmap)
        }
        else {
            Log.d("New Canvas", "New Canvas")
            myViewModel.updateBitmap(Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentDrawingBinding.inflate(layoutInflater, container, false)

        // Create a CanvasView with the view model in it
        val canvasView = CanvasView(requireContext(), null, myViewModel)
        binding.canvasHolderID.addView(canvasView)

        // Setting navigation for back button
        binding.backButtonID.setOnClickListener{
            findNavController().navigate(R.id.action_back)
        }
        // Setting save button
        binding.saveButtonID.setOnClickListener{
            val drawingTitle = binding.fileNameEntry.text.toString()

            if (drawingTitle.isBlank()) {
                // Show the popup if title is empty
                AlertDialog.Builder(requireContext())
                    .setTitle("Save Failed")
                    .setMessage("Your drawing is untitled. Please add a title to save.")
                    .setNegativeButton("Close") { dialog, _ ->
                        dialog.dismiss() // Close the dialog
                    }
                    .show()
            } else {
                myViewModel.getBitmap()
                    ?.let { it1 -> selectDrawingViewModel.saveDrawing(it1, drawingTitle) }
            }

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