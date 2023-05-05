package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.myapplication.databinding.ActivityRegCustomerBinding
import com.google.firebase.auth.FirebaseAuth

class RegCustomer : AppCompatActivity() {

    private lateinit var binding:ActivityRegCustomerBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegCustomerBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_reg_customer)

        firebaseAuth = FirebaseAuth.getInstance()



        binding.regCusButton.setOnClickListener{
            val fname = binding.cusFName.text.toString()
            val lname = binding.cusLName.text.toString()
            val email = binding.emailCus.text.toString()
            val pass = binding.passwordCus.text.toString()
            val confirmPass = binding.rePasswordCus.text.toString()

            if ( fname.isNotEmpty() && lname.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()){
                if(pass==confirmPass){
                    firebaseAuth.createUserWithEmailAndPassword(email,pass,).addOnCompleteListener{
                        if(it.isSuccessful){
                            val intent = Intent(this, Login::class.java);
                            startActivity(intent)

                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

                }else{
                    Toast.makeText(this,"Password is not matching!", Toast.LENGTH_SHORT).show()
                }
            }else{
            Toast.makeText(this,"Empty Fields Are Not Allowed!!", Toast.LENGTH_SHORT).show()
        }
        }


        val cancelRegShopButton = findViewById<Button>(R.id.cancelRegCusButton);

        cancelRegShopButton.setOnClickListener {
            val nextPage = Intent(this, Login::class.java);
            startActivity(nextPage);
        }

    }
}