package com.example.drawingapp

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.fragment.findNavController
import com.example.drawingapp.databinding.FragmentCloudBinding
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Date
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CloudFragment : Fragment() {

    private val converter = BitmapConverter()
    private val viewModel: SelectDrawingViewModel by viewModels{
        SelectDrawingViewModelFactory((requireActivity().application as DrawingApplication).drawingRepository)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCloudBinding.inflate(layoutInflater, container, false)
        val viewModel: SelectDrawingViewModel by viewModels{
            SelectDrawingViewModelFactory((requireActivity().application as DrawingApplication).drawingRepository)}

        binding.loginView.setContent {
            LoginView(findNavController())
        }

        //sets up composeview for composeUI
        binding.cloudFileList.setContent {
            FileItemList(viewModel, converter, findNavController())
        }

        return binding.root
    }

    @Composable
    fun LoginView(navController: NavController) {
        var user by rememberSaveable { mutableStateOf(Firebase.auth.currentUser) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp), // Adjust the height for a small box
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp), // Add some padding to the edges
                horizontalArrangement = Arrangement.SpaceBetween, // Space out the buttons
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = {
                    navController.navigate(R.id.action_back) // Replace with your navigation action
                }) {
                    Text("Back")
                }
                if (user != null) {
                    Button(onClick = {
                        Firebase.auth.signOut()
                        user = null
                    }) {
                        Text("Sign Out")
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(48.dp),
            verticalArrangement = Arrangement.Top
        ) {
            if (user == null) {
                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }

                // Username Text Box
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                // Password Text Box
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Button(
                        onClick = {
                            Firebase.auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(requireActivity()) { task ->
                                    if (task.isSuccessful) {
                                        user = Firebase.auth.currentUser
                                    }
                                }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Log In")
                    }

                    Button(
                        onClick = {
                            Firebase.auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(requireActivity()) { task ->
                                    if (task.isSuccessful) {
                                        user = Firebase.auth.currentUser
                                        val document = mapOf(
                                            "uid" to user!!.uid,
                                            "name" to "",
                                            "time" to Date()
                                        )
                                        lifecycleScope.launch {
                                            uploadDocument(user!!.uid, document)
                                        }
                                    } else {
                                        Log.e("AUTHENTICATION ERROR", "${task.exception}")
                                    }
                                }


                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Sign Up")
                    }


                }
            } else {
                val myViewModel: CloudViewModel by viewModels()
                val list = myViewModel.cloudDrawings

                val db = FirebaseFirestore.getInstance()
                val userInfo = db.collection("users").document(user!!.uid)
                var name = ""
                userInfo.get().addOnSuccessListener { document ->
                    if(document != null && document.exists()) {
                        name = document.getString("name").toString()
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Log.d("DOWNLOAD", "LOGGED IN!")
                    DownloadableList(list, viewModel.repository)

                }

            }
        }
    }

    suspend fun uploadDocument(id: String, document: Any) {
        val db = FirebaseFirestore.getInstance()
        suspendCoroutine { continuation ->
            db.collection("users/").document(id)
                .set(document)
                .addOnSuccessListener {
                    Log.e("UPLOAD", "SUCCESSFUL!")
                    continuation.resume(Unit)
                }
                .addOnFailureListener {
                        e -> Log.e("UPLOAD", "FAILED!: $e")
                    continuation.resume(Unit)
                }
        }
    }

}