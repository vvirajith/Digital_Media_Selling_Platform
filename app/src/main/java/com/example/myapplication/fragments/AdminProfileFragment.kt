package com.example.myapplication.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.Login
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAdminProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminViewModel  : ViewModel() {
    var email: String? = null
    var fname: String? = null
    var lname: String? = null
}

class AdminProfileFragment : Fragment() {

    private var _binding: FragmentAdminProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminProfileBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        return binding.root
    }

    private val userViewModel by lazy {
        ViewModelProvider(this).get(AdminViewModel::class.java)
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        val ref = FirebaseDatabase.getInstance().getReference("Users")

        if (firebaseUser == null) {
            startActivity(Intent(requireContext(), Login::class.java))
            requireActivity().finish()
        }else{
            userViewModel.email = firebaseUser.email
            binding.emailText.text = userViewModel.email

            ref.child(firebaseUser.uid)
                .addListenerForSingleValueEvent(object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {

                        userViewModel.fname= snapshot.child("fname").value as String?
                        userViewModel.lname= snapshot.child("lname").value as String?
                        binding.nameText.text = "${userViewModel.fname} ${userViewModel.lname}"
                    }
                    override fun onCancelled(error: DatabaseError) {

                    }


                } )


        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logoutbtn.setOnClickListener{
            firebaseAuth.signOut()
            startActivity(Intent(requireContext(), Login::class.java))
            requireActivity().finish()
        }

    }


}