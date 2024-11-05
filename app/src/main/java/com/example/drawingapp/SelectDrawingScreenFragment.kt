package com.example.drawingapp

import android.graphics.Bitmap
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.drawingapp.databinding.FragmentMainScreenBinding

class SelectDrawingScreenFragment : Fragment() {

    private val converter = BitmapConverter()
    private val viewModel: SelectDrawingViewModel by viewModels{
        SelectDrawingViewModelFactory((requireActivity().application as DrawingApplication).drawingRepository)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainScreenBinding.inflate(layoutInflater, container, false)

        //setting up navigation for opening drawing screen MAY CHANGE LATER
        binding.newDrawingBtn.setOnClickListener{
            findNavController().navigate(R.id.action_open_drawing)
        }

        //setting up navigation for opening cloud screen MAY CHANGE LATER
        binding.openCloudBtn.setOnClickListener{
            findNavController().navigate(R.id.action_open_cloud)
        }

        // This button is just here to demo that the saving file works.
        binding.saveDrawingDemoBtn.setOnClickListener{
            // This is an example of how to save a drawing to the data base.
            viewModel.saveDrawing(Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888), "APPLE")
        }

        // This button demos loading a drawing.
        binding.loadDrawingDemoBtn.setOnClickListener{
            viewModel.loadDrawing("APPLE") { drawing ->
                val bundle = Bundle().apply {
                    putByteArray("bitmap", converter.fromBitmap(drawing))
                }
                findNavController().navigate(R.id.action_open_drawing, bundle)
            }
        }

        // This button clears the DAO for testing purposes.
        binding.clearDAOBtn.setOnClickListener{
            viewModel.clearDrawings()
        }

         // This is where you can update the list of drawings selectable.
//         viewModel.drawings.observe(viewLifecycleOwner){
//         }

        return binding.root
    }
}