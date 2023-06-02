package com.gultendogan.gulten_dogan_odev6.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gultendogan.gulten_dogan_odev6.ProductsActivity
import com.gultendogan.gulten_dogan_odev6.databinding.RowLayoutBinding
import com.gultendogan.gulten_dogan_odev6.model.Product

class RecyclerViewAdapter(private val productList: List<Product>, private val listener: ProductsActivity) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    interface OnItemClickListener {
        fun onItemClick(product: Product)
    }

    class RowHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RowHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.count()
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener.onItemClick(productList.get(position))
        }
        holder.binding.productNameText.text = productList.get(position).title
        holder.binding.productPriceText.text = "Price: " + productList.get(position).price.toString() + "$"
        holder.binding.productRatingText.text = "Rating: " + productList.get(position).rating.toString()
        Glide.with(holder.itemView.context)
            .load(productList.get(position).images[0])
            .into(holder.binding.productImage)

    }
}