package com.example.ppapb.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.ppapb.activity.MainActivity
import com.example.ppapb.admin.MainAdmin
import com.example.ppapb.databinding.FragmentLoginBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = Firebase.auth

        val email: TextInputEditText = binding.edtEmail
        val password: TextInputEditText = binding.edtPassword
        val btnLogin: Button = binding.btnLogin

        // Check if user credentials are saved in SharedPreferences
        val sharedPreferences =
            requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val savedEmail = sharedPreferences.getString("email", null)
        val savedPassword = sharedPreferences.getString("password", null)
        val editor = sharedPreferences.edit()

        btnLogin.setOnClickListener {
            if (email.text.toString().isEmpty()) {
                Toast.makeText(
                    requireActivity(),
                    "Anda belum mengisi E-mail anda!",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (password.text.toString().isEmpty()) {
                Toast.makeText(
                    requireActivity(),
                    "Anda belum mengisi Password anda!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Lakukan login hanya jika email dan password tidak kosong
                auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(requireActivity()) { taskk ->
                        val currentUser = auth.currentUser
                        if (taskk.isSuccessful) {
                            if (currentUser != null) {
                                // Save user credentials in SharedPreferences
                                // Check if the user is an admin based on their email address
                                if (currentUser.email == "adminbloblo@gmail.com") {
                                    // User is an admin, redirect to Admin activity
                                    editor.putString("email", email.text.toString())
                                    editor.putString("password", password.text.toString())
                                    editor.apply()
                                    val intent = Intent(requireActivity(), MainAdmin::class.java)
                                    startActivity(intent)
                                }else {
                                    // User is not an admin, redirect to regular MainActivity
                                    editor.putString("email", email.text.toString())
                                    editor.putString("password", password.text.toString())
                                    editor.apply()
                                    val intent = Intent(requireActivity(), MainActivity::class.java)
                                    startActivity(intent)

                                }
                            }
                        } else {
                            Toast.makeText(requireActivity(), "Login Failed", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }
        }
        return binding.root
    }
}