package com.example.ppapb.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.ppapb.R
import com.example.ppapb.activity.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private lateinit var userEmail: TextView
    private lateinit var logoutButton: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Ambil referensi ke elemen-elemen dalam layout
        auth = FirebaseAuth.getInstance()
        userEmail = view.findViewById(R.id.email)
        logoutButton = view.findViewById(R.id.logoutButton)

        // Set teks email dengan alamat email dari pengguna yang login
        val user = auth.currentUser
        val userEmailText = user?.email ?: "Email"
        userEmail.text = userEmailText

        // Tambahkan fungsi untuk menangani logout saat tombol logout ditekan
        logoutButton.setOnClickListener {
            logoutAndNavigateToLogin()
        }

        return view
    }

    private fun logoutAndNavigateToLogin() {
        // Logout menggunakan FirebaseAuth
        auth.signOut()

        // Navigasi ke halaman login
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}
