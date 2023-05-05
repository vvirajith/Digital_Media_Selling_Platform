package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.databinding.ActivityRegCustomerBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.signin.setOnClickListener{
            val nextPage = Intent(this, RegCustomer::class.java)
            startActivity(nextPage)
        }

        binding.submitLoginButton.setOnClickListener{
            val email = binding.email.text.toString()
            val pass = binding.passwordCus.text.toString()


            if (email.isNotEmpty() && pass.isNotEmpty()){

                    firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener{
                        if(it.isSuccessful){
                            val intent = Intent(this, NavigationBar::class.java);
                            startActivity(intent)

                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }


            }else{
                Toast.makeText(this,"Empty Fields Are Not Allowed!!", Toast.LENGTH_SHORT).show()
            }
        }





        val RegNow = findViewById<Button>(R.id.regNowButton);

        RegNow.setOnClickListener {
            val nextPage = Intent(this, RegisterAs::class.java)
            startActivity(nextPage)

        }



    }
    override fun onStart(){
        super.onStart()
        if(firebaseAuth.currentUser != null){
            val intent = Intent(this, NavigationBar::class.java);
            startActivity(intent)
        }

    }
}