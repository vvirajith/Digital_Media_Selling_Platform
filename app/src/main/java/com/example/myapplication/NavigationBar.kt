package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.databinding.ActivityNavigationBarBinding
import com.example.myapplication.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class NavigationBar : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationBarBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private var userType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        checkUser { retrievedUserType ->
            userType = retrievedUserType
            }
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        val homeFragment = HomeFragment()
        val cartFragment = CartFragment()
        val notificationsFragment = NotificationsFragment()
        val profileFragment = ProfileFragment()
        val shopProfileFragment = ShopProfileFragment()
        val adminProfileFragment = AdminProfileFragment()

        makeCurrentFragment(homeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.iconhome -> makeCurrentFragment(homeFragment)
                R.id.iconcart -> makeCurrentFragment(cartFragment)
                R.id.iconnotification -> makeCurrentFragment(notificationsFragment)
                R.id.iconprofile -> {
                    if (userType == "user") {
                        makeCurrentFragment(profileFragment)
                    } else if (userType == "shop") {
                        makeCurrentFragment(shopProfileFragment)
                    } else if (userType == "admin") {
                        makeCurrentFragment(adminProfileFragment)
                    }

                }
            }
            true
        }
    }

    private fun checkUser(completion: (String?) -> Unit) {
        val firebaseUser = firebaseAuth.currentUser!!

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseUser.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    userType = snapshot.child("userType").value as? String
                    Log.d("checkUser", "userType: $userType")
                    completion(userType)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("checkUser", "Error retrieving user type", error.toException())

                }
            })
    }



    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
}
