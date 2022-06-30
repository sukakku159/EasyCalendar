package com.simplemobiletools.calendar.pro.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.simplemobiletools.calendar.pro.activities.MainActivity
import com.simplemobiletools.calendar.pro.databinding.FragmentLoginBinding

class FragmentLogin : BaseFragment<FragmentLoginBinding>() {
    override fun getViewBinding(): FragmentLoginBinding = FragmentLoginBinding.inflate(layoutInflater)
    private lateinit var firebaseAuth : FirebaseAuth
    private var username = ""
    private var password = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        validateData()
    }
     private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            //user is already logged in
            startActivity(Intent(requireContext(), MainActivity :: class.java))

        }
    }
    private fun validateData() {
        username  = binding.edtUsername.text.toString().trim()
        password = binding.edtPassword.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            binding.edtUsername.error = "Invalid email format"
        }
        else if (TextUtils.isEmpty(password)) {
            binding.edtPassword.error = "Please enter password"
        }
        else {
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        firebaseAuth.signInWithEmailAndPassword(username, password)
            .addOnSuccessListener {
                Toast.makeText(requireActivity(), "Login success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireActivity(), "Login failed due to ${it.message}", Toast.LENGTH_SHORT).show()
            }


    }

}
