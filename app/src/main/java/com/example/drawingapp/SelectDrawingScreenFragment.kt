package com.example.drawingapp

import android.graphics.Bitmap
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
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

        binding.composeFileList.setContent {
            FileItemList(viewModel, converter, findNavController())
        }
        //setting up navigation for opening drawing screen MAY CHANGE LATER
        binding.newDrawingBtn.setOnClickListener{
            findNavController().navigate(R.id.action_open_drawing)
        }

        //setting up navigation for opening cloud screen MAY CHANGE LATER
        binding.openCloudBtn.setOnClickListener{
            findNavController().navigate(R.id.action_open_cloud)
        }

         // This is where you can update the list of drawings selectable.
//         viewModel.drawings.observe(viewLifecycleOwner){
//         }

        return binding.root
    }
}