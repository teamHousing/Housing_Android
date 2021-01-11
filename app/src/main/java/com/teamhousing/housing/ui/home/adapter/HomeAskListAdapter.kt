package com.teamhousing.housing.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.RvItemHomeAskListBinding
import com.teamhousing.housing.vo.HomeAskListData

class HomeAskListAdapter(private val context: Context) : RecyclerView.Adapter<HomeAskListViewHolder>() {
    var data = mutableListOf<HomeAskListData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAskListViewHolder {
        val binding : RvItemHomeAskListBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.rv_item_home_ask_list,
                parent,
                false
        )

        return HomeAskListViewHolder(
                binding
        )
    }

    override fun onBindViewHolder(holder: HomeAskListViewHolder, position: Int) {
        data[position].let{
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data.size

}

class HomeAskListViewHolder(val binding:RvItemHomeAskListBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(item : HomeAskListData){
        binding.item = item
    }
}