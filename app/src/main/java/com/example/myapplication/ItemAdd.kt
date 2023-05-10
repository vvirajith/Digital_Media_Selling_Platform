package com.example.myapplication

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityCategoryAddBinding
import com.example.myapplication.databinding.ActivityItemAddBinding
import com.example.myapplication.fragments.ShopProfileFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ItemAdd : AppCompatActivity() {

    private lateinit var binding: ActivityItemAddBinding

    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //progress dialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemAddBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //init // firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        // configure progress dialog

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("please wait....")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.CancelButton.setOnClickListener {
            val nextPage = Intent(this, NavigationBar ::class.java);
            startActivity(nextPage);
        }

        //handle click , go back
        binding.addItemBtn.setOnClickListener {
            onBackPressed()
        }

        //handle click,  begin upload category
        binding.addItemBtn.setOnClickListener {
            validateData()
        }

    }


    private var item_name = ""
    private var item_des = ""
    private var item_category=""
    private  var item_mycategory=""
    private var item_price=""

    private fun validateData() {
        //validate data

        //get data
        item_name = binding.itemName.text.toString().trim()
        item_des = binding.itemDescrip.text.toString().trim()
        item_category= binding.itemcate.text.toString().trim()
        item_mycategory =binding.mycategory.text.toString().trim()
        item_price = binding.itemprice.text.toString().trim()


        //validate data
        if (item_name.isEmpty()) {
            Toast.makeText(this, "Enter Item...", Toast.LENGTH_SHORT).show()
        }else if (item_des.isEmpty()) {
            Toast.makeText(this, "Enter Item description...", Toast.LENGTH_SHORT).show()
        }else if(item_category.isEmpty()) {
            Toast.makeText(this, "Enter Item category...", Toast.LENGTH_SHORT).show()
        } else if(item_mycategory.isEmpty()) {
            Toast.makeText(this, "Enter Item mycategory...", Toast.LENGTH_SHORT).show()
        }
        else if(item_price.isEmpty()) {
            Toast.makeText(this, "Enter Item price...", Toast.LENGTH_SHORT).show()
        }else{
            AddItemFirebase()
        }


    }

    private fun AddItemFirebase() {
        //show progress
        progressDialog.show()

        //get timestamp
        val timestamp = System.currentTimeMillis()

        // setting up data to add in firebase DB
        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "$timestamp"
        hashMap["itemName"]= item_name
        hashMap["itemDescription"]= item_des
        hashMap["itemCategory"]= item_category
        hashMap["itemMyCategory"]= item_mycategory
        hashMap["itemPrice"]= item_price
        hashMap["timestamp"] = timestamp
        hashMap["uid"] = "${firebaseAuth.uid}"

        //add to firebase DB: Database Root > categoryID  >category INfo
        val ref = FirebaseDatabase.getInstance().getReference("items")
        ref.child("$timestamp")
            .setValue(hashMap)
            .addOnCompleteListener {
                // added successfully
                progressDialog.dismiss()
                Toast.makeText(this, "Added Successfully...", Toast.LENGTH_SHORT).show()


            }
            .addOnFailureListener {e->
                //failed to add
                progressDialog.dismiss()
                Toast.makeText(this, "Failed to add due to ${e.message}", Toast.LENGTH_SHORT).show()

            }


    }

}