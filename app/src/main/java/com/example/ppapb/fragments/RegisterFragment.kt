package com.example.ppapb.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.ppapb.databinding.FragmentRegisterBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    private lateinit var binding : FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var sharePreferences : SharedPreferences

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        auth = Firebase.auth

        sharePreferences = requireActivity().getSharedPreferences("user_data",Context.MODE_PRIVATE)
        val editor = sharePreferences.edit()

        val email: TextInputEditText = binding.edtEmail
        val username: TextInputEditText = binding.edtUsername
        val password: TextInputEditText = binding.edtPassword
        val registerNow: Button = binding.btnLogin

        registerNow.setOnClickListener {
            if (email.text.toString().isEmpty()) {
                Toast.makeText(requireActivity(), "PLEASE FILL THE EMAIL", Toast.LENGTH_SHORT).show()
            } else if (password.text.toString().isEmpty()) {
                Toast.makeText(requireActivity(), "PLEASE FILL THE PASSWORD", Toast.LENGTH_SHORT).show()
            } else {
                // Validasi berhasil, lanjutkan ke proses pendaftaran
                auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            editor.putString("username", username.text.toString())
                            editor.putString("email", email.text.toString())
                            editor.putString("password", password.text.toString())
                            editor.apply()

                            email.text?.clear()
                            password.text?.clear()
                            username.text?.clear()
                            Toast.makeText(requireActivity(), "Register Successful!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireActivity(), "Register Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}