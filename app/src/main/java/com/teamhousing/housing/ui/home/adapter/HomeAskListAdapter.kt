package com.teamhousing.housing.ui.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.RvItemHomeAskListBinding
import com.teamhousing.housing.ui.home.detail.HomeDetailActivity
import com.teamhousing.housing.vo.AskItem

class HomeAskListAdapter(private val context: Context) : RecyclerView.Adapter<HomeAskListViewHolder>() {
    var data = mutableListOf<AskItem>()

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

        holder.itemView.setOnClickListener {
            val detailIntent = Intent(context, HomeDetailActivity::class.java)
            detailIntent.putExtra("id", data[position].id)

            context.startActivity(detailIntent)
        }
    }

    override fun getItemCount(): Int = data.size

    fun replaceAskList(list : MutableList<AskItem>){
        data = list
        notifyDataSetChanged()
    }

}

class HomeAskListViewHolder(val binding:RvItemHomeAskListBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(item : AskItem){
        binding.item = item
    }
}