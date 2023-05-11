package com.example.myapplication

import android.app.AlertDialog
import androidx.activity.result.ActivityResult
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myapplication.databinding.ActivityCategoryAddBinding
import com.example.myapplication.databinding.ActivityItemAddBinding
import com.example.myapplication.fragments.ShopProfileFragment
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlin.math.log

class ItemAdd : AppCompatActivity() {

    private lateinit var binding: ActivityItemAddBinding

    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //progress dialog
    private lateinit var progressDialog: ProgressDialog

    private lateinit var categoryArrayList: ArrayList<ModalCategory>

    private var pdfUri: Uri? = null
    private var TAG = "PDF_ADD_Tag"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemAddBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //init // firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        loadPdfCategories()

        // configure progress dialog

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("please wait....")
        progressDialog.setCanceledOnTouchOutside(false)

        //handle click,goback
        binding.CancelButton.setOnClickListener{
            onBackPressed()
        }

        binding.CancelButton.setOnClickListener {
            val nextPage = Intent(this, NavigationBar ::class.java);
            startActivity(nextPage);
        }

        binding.itemcate.setOnClickListener{
            categoryPickDialog()
        }

        //handle click , go back
        binding.addItemBtn.setOnClickListener {
            onBackPressed()
        }

        //handle click,  begin upload category
        binding.addItemBtn.setOnClickListener {
            validateData()
        }

        //handle pick pdf

        binding.attachPDFBtn.setOnClickListener{
            pdfPickIntent()


            validateData()

        }

    }

    private fun pdfPickIntent() {
        Log.d(TAG,"pdfPickIntent:string pdf pick intent")

        val intent = Intent()
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        pdfActivityRestultLauncher.launch(intent)
    }

    val pdfActivityRestultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> {result ->
            if(result.resultCode == RESULT_OK){
                Log.d(TAG,"PDF Picked")
                pdfUri = result.data?.data
            }
            else{
                Log.d(TAG,"PDF Pick cancelled")
                Toast.makeText(this,"Cancelled",Toast.LENGTH_SHORT).show()
            }
        }
    )


    private fun loadPdfCategories() {
        Log.d(TAG,"LoadPdfCategories: Loading pdf categories")
        categoryArrayList = ArrayList()

        val ref = FirebaseDatabase.getInstance().getReference("categories")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                categoryArrayList.clear()
                for (ds in snapshot.children){
                    val model = ds.getValue(ModalCategory::class.java)

                    categoryArrayList.add(model!!)
                    Log.d(TAG,"onDataChange: ${model.category}")
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    private var selectedCategoryId = ""
    private var selectedCategoryTitle = ""
    private fun categoryPickDialog(){
        Log.d(TAG,"categoryPickDialog: Showing category pick dialog")

        val categoriesArray = arrayOfNulls<String>(categoryArrayList.size)
        for (i in categoryArrayList.indices){
            categoriesArray[i] = categoryArrayList[i].category
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pick Category")
            .setItems(categoriesArray){dialog, which ->
                selectedCategoryTitle = categoryArrayList[which].category
                selectedCategoryId = categoryArrayList[which].id

                binding.itemcate.text = selectedCategoryTitle

                Log.d(TAG, "categoryPickDialog: Selected Category ID: $selectedCategoryId")
                Log.d(TAG, "categoryPickDialog: Selected Category Title: $selectedCategoryTitle")

            }
            .show()
    }


    private var item_name = ""
    private var item_des = ""
    private var item_category=""
    private  var item_mycategory=""
    private var item_price=""

    private fun AddItemFirebase() {
        Log.d(TAG,"AddItemFirebase:uploading to storage...")

        //show progress
        progressDialog.setMessage("Uploading PDF.")
        progressDialog.show()


        //get timestamp
        val timestamp = System.currentTimeMillis()

        //val path of pdf
        val filePathName = "items/$timestamp"
        //storage referance
        val storageReference = FirebaseStorage.getInstance().getReference(filePathName)
        storageReference.putFile(pdfUri!!)
            .addOnSuccessListener {taskSnapshot ->
                Log.d(TAG,"UploadPdfStorage:PDF Uploaded now getting url...")

                //get url of upload
                val uriTask: Task<Uri> = taskSnapshot.storage.downloadUrl
                while (!uriTask.isSuccessful);
                val uploadPdfUrl="${uriTask.result}"

                uploadPdfInfoToDb(uploadPdfUrl,timestamp)

            }
            .addOnFailureListener{e->
                Log.d(TAG,"uploadPdfToStorage:failed to upload due to ${e.message}")
                progressDialog.dismiss()
                Toast.makeText(this,"Failed to upload due to ${e.message}",Toast.LENGTH_SHORT).show()
            }



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
        }
        else if (pdfUri == null){
            Toast.makeText(this,"pick PDF",Toast.LENGTH_SHORT).show()
        }
        else{
            AddItemFirebase()
        }


    }

    private fun uploadPdfInfoToDb(uploadPdfUri: String, timestamp: Long) {
        //upload pdf into firebase db
        Log.d(TAG,"uploadPdfInfoToDb")
        progressDialog.setMessage("uploading pdf info...")

       //uid of current user
        val uid = firebaseAuth.uid

        //setup data to upload
        val hashMap: HashMap<String, Any> =HashMap()
        hashMap["uid"]="$uid"
        hashMap["id"]="$timestamp"
        hashMap["title"]="$title"
        hashMap["description"]="$item_des"
        hashMap["url"]="$uploadPdfUri"
        hashMap["timestamp"]=timestamp
        hashMap["viewsCounter"]=0
        hashMap["downloadCounter"]=0

        //db referance DB>

        val ref = FirebaseDatabase.getInstance().getReference("items")
        ref.child("$timestamp")
            .setValue(hashMap)
            .addOnCompleteListener {
                Log.d(TAG, "uploadPdfInfoToDb: uploaded to db")
                progressDialog.dismiss()
                Toast.makeText(this,"Uploaded...",Toast.LENGTH_SHORT).show()
                pdfUri=null

            }
            .addOnFailureListener{e->
                Log.d(TAG,"uploadPdfinfoToStoDb:failed to upload due to ${e.message}")
                progressDialog.dismiss()
                Toast.makeText(this,"Failed to upload due to ${e.message}",Toast.LENGTH_SHORT).show()


    }



}
}