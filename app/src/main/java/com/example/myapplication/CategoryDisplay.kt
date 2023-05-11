package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.myapplication.databinding.ActivityCategoryAddBinding
import com.example.myapplication.databinding.ActivityCategoryDisplayBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CategoryDisplay : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryDisplayBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var categoryArrayList: ArrayList<ModalCategory>
    private lateinit var adapterCategory: AdapterCategory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryDisplayBinding.inflate(layoutInflater)

        setContentView(binding.root)
        
        firebaseAuth = FirebaseAuth.getInstance()

        binding.addCatBtn.setOnClickListener{
            val intent = Intent(this, CategoryAdd::class.java)
            startActivity(intent)
        }

        loadCategories()
        
        binding.searchET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    adapterCategory.filter.filter(s)

                    binding.categoriesRv.scrollToPosition(35)

                }catch (e: Exception){

                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun loadCategories() {
        categoryArrayList = ArrayList()

        val ref = FirebaseDatabase.getInstance().getReference("categories")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                categoryArrayList.clear()
                for (ds in snapshot.children) {
                    val model = ds.getValue(ModalCategory::class.java)

                    categoryArrayList.add(model!!)

                }

                adapterCategory = AdapterCategory(this@CategoryDisplay, categoryArrayList)
                binding.categoriesRv.adapter = adapterCategory
            }

            override fun onCancelled(error: DatabaseError) {

            }

            })


    }
}