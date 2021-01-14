package com.teamhousing.housing.ui.home.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.RvItemHomeDetailInfoBinding
import com.teamhousing.housing.vo.InfoCommunicationListData

class InfoCommunicationListAdapter (private val context: Context) : RecyclerView.Adapter<InfoCommunicationListViewHolder>() {
    var data = mutableListOf<InfoCommunicationListData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoCommunicationListViewHolder {
        val binding : RvItemHomeDetailInfoBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.rv_item_home_detail_info,
                parent,
                false
        )

        return InfoCommunicationListViewHolder(
                binding
        )
    }

    override fun onBindViewHolder(holder: InfoCommunicationListViewHolder, position: Int) {
        data[position].let{
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data.size

    fun replaceAskList(list : MutableList<InfoCommunicationListData>){
        data = list
        notifyDataSetChanged()
    }

}

class InfoCommunicationListViewHolder(val binding:RvItemHomeDetailInfoBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(item : InfoCommunicationListData){
        binding.item = item
    }
}