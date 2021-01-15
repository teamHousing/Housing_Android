package com.teamhousing.housing.ui.home.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.RvItemHomeDetailInfoBinding
import com.teamhousing.housing.databinding.RvItemHomeDetailPhotoBinding
import com.teamhousing.housing.vo.InfoCommunicationListData

class InfoPhotoListAdapter (private val context: Context) : RecyclerView.Adapter<InfoPhotoListViewHolder>() {
    var data = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoPhotoListViewHolder {
        val binding : RvItemHomeDetailPhotoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_home_detail_info,
            parent,
            false
        )

        return InfoPhotoListViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: InfoPhotoListViewHolder, position: Int) {
        data[position].let{
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data.size

    fun replacePhotoList(list : MutableList<String>){
        data = list
        notifyDataSetChanged()
    }

}

class InfoPhotoListViewHolder(val binding: RvItemHomeDetailPhotoBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(item : String){
        binding.item = item
        binding.executePendingBindings()
    }
}