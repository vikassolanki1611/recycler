package com.recyclerapplication

import android.annotation.SuppressLint
import android.content.Context
import android.icu.number.NumberFormatter.with

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recyclerapplication.databinding.ItemListRowBinding
import com.recyclerapplication.response.Search
import com.squareup.picasso.Picasso

class ListAdapter(
    val dataList: ArrayList<Search>,
    val mContext: Context
): RecyclerView.Adapter<ListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListAdapter.ViewHolder {
        val MoreProductsAdapter = LayoutInflater.from(parent.context)
        val binding = ItemListRowBinding.inflate(MoreProductsAdapter, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (dataList.isNullOrEmpty()) 0 else dataList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {

        val dataModel = dataList[position]
        //Glide.with(mContext).load(dataModel.poster).into(holder.binding.posterIV)
        holder.binding.movieNameTV.text=dataModel.title
        holder.binding.movieYearTV.text=dataModel.year


        Picasso.get().load(dataModel.poster)
            .placeholder(R.drawable.ic_no_image) //this is optional the image to display while the url image is downloading
            .error(R.drawable.ic_no_image) //this is also optional if some error has occurred in downloading the image this image would be displayed
            .into(holder.binding.posterIV)

    }


    inner class ViewHolder(val binding: ItemListRowBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }



}


