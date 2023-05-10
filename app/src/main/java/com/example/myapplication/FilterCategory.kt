package com.example.myapplication

import android.widget.Filter

class FilterCategory: Filter{
    private var filterList: ArrayList<ModalCategory>

    private var adapterCategory: AdapterCategory

    constructor(filterList: ArrayList<ModalCategory>, adapterCategory: AdapterCategory) : super() {
        this.filterList = filterList
        this.adapterCategory = adapterCategory
    }
    override fun performFiltering(constraint: CharSequence?): FilterResults {
        var constraint = constraint
        val result = FilterResults()

        if (constraint !=null && constraint.isNotEmpty()){
            constraint= constraint.toString().uppercase()
            val filterModel: ArrayList<ModalCategory> = ArrayList()
            for (i in 0 until filterList.size){
                if (filterList[i].category.uppercase().contains(constraint)){
                    filterModel.add(filterList[i])
                }
            }
            result.count = filterModel.size
            result.values = filterModel
        }
        else{
            result.count = filterList.size
            result.values = filterList
        }
        return result
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults) {
        adapterCategory.categoryArrayList = results.values as ArrayList<ModalCategory>

        adapterCategory.notifyDataSetChanged()

    }
}

