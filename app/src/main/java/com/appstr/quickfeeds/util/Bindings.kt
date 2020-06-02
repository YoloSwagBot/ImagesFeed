package com.appstr.quickfeeds.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView


@BindingAdapter("adapter")
fun setAdapter(recycler: RecyclerView, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?){
    recycler.adapter = adapter
}
