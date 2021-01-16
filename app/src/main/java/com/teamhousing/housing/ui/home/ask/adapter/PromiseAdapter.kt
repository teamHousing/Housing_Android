package com.teamhousing.housing.ui.home.ask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.teamhousing.housing.BR
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.ItemPromiseBinding
import com.teamhousing.housing.vo.PromiseData

class PromiseAdapter : RecyclerView.Adapter<PromiseAdapter.VHolder>() {

    var data: MutableList<PromiseData> = mutableListOf() // 뷰모델에 넣을 리스트 (리싸이클러뷰)
    var data2: MutableList<PromiseData> = mutableListOf() // 서버에 보낼 리스트

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_promise, parent, false)
        return VHolder(view)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int = data.size

    inner class VHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val binding : ItemPromiseBinding = DataBindingUtil.bind(itemView)!!
        fun onBind(position: Int) {
            binding.setVariable(BR.data , data[position])
            binding.btnDeletePromiseItem.setOnClickListener {
                data.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, data.size)
                data2.removeAt(position)
            }
        }
    }
}