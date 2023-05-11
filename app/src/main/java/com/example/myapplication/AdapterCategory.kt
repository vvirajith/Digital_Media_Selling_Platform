package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.RowAdminCategoryBinding
import com.google.firebase.database.FirebaseDatabase

class AdapterCategory: RecyclerView.Adapter<AdapterCategory.HolderCategory>, Filterable {


    private val context: Context
    public var categoryArrayList: ArrayList<ModalCategory>
    private var filterList: ArrayList<ModalCategory>

    private var filter: FilterCategory? =null

    private lateinit var binding: RowAdminCategoryBinding

    constructor(context: Context, categoryArrayList: ArrayList<ModalCategory>) {
        this.context = context
        this.categoryArrayList = categoryArrayList
        this.filterList = categoryArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderCategory {
        binding = RowAdminCategoryBinding.inflate(LayoutInflater.from(context),parent,false)
        return HolderCategory(binding.root)
    }

    override fun getItemCount(): Int {
        return categoryArrayList.size
    }

    override fun onBindViewHolder(holder: HolderCategory, position: Int) {
        val model = categoryArrayList[position]
        val id = model.id
        val category = model.category
        val uid = model.uid
        val timestamp = model.timestamp

        holder.categoryTv.text= category

        holder.deletedbtn.setOnClickListener{
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete")
                .setMessage("Are you Sure?")
                .setPositiveButton("Confirm"){a, d->
                    Toast.makeText(context, "Deleting..", Toast.LENGTH_SHORT).show()
                    deleteCategory(model,holder)
                }
                .setNegativeButton("Cancel"){a, d->
                    a.dismiss()
                }
                .show()
        }
    }

    private fun deleteCategory(model: ModalCategory, holder: HolderCategory) {
        val id = model.id
        val ref = FirebaseDatabase.getInstance().getReference("categories")
        ref.child(id)
            .removeValue()
            .addOnSuccessListener {
                Toast.makeText(context, "Deleted Successful", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener{ e ->
                Toast.makeText(context, "Deleting Unsuccessful due to ${e.message} ", Toast.LENGTH_SHORT).show()

            }
    }

    //viewHolder class to hold / init UI views for raw_admin_category.xml
    inner class  HolderCategory(itemView:View):RecyclerView.ViewHolder(itemView){
        //init UI Views
        var categoryTv: TextView = binding.categoryTv
        var deletedbtn: ImageButton = binding.deleteBtn

    }

    override fun getFilter(): Filter {
        if(filter == null){
            filter= FilterCategory(filterList,this)
        }
        return filter as FilterCategory
    }


}