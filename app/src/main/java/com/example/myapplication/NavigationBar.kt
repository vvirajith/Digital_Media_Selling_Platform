package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.myapplication.fragments.CartFragment
import com.example.myapplication.fragments.HomeFragment
import com.example.myapplication.fragments.NotificationsFragment
import com.example.myapplication.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class NavigationBar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_bar)
        val BottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

       val homeFragment = HomeFragment()
        val cartFragment = CartFragment()
        val notificationsFragment = NotificationsFragment()
        val profileFragment =ProfileFragment()


        makeCurrentFragment(homeFragment)

        BottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.iconhome -> makeCurrentFragment(homeFragment)
                R.id.iconcart -> makeCurrentFragment(cartFragment)
                R.id.iconnotification -> makeCurrentFragment(notificationsFragment)
                R.id.iconprofile -> makeCurrentFragment(profileFragment)

            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper,fragment)
            commit()
        }
}