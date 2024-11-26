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
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class CloudFragment : Fragment() {

    private val viewModel: CloudViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCloudBinding.inflate(layoutInflater, container, false)

        //setting navigation on back button
        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_back)
        }

        binding.loginView.setContent {
            LoginView()
        }

        return binding.root
    }


    @Composable
    fun LoginView() {
        var user by rememberSaveable { mutableStateOf(Firebase.auth.currentUser) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
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
                Button(onClick = {
                    Firebase.auth.signOut()
                    user = null
                }) {
                    Text("Sign Out")
                }
            }
        }
    }
}