package com.teamhousing.housing.ui.home.ask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.teamhousing.housing.BR
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.ItemContactBinding
import com.teamhousing.housing.vo.ContactData

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.VHolder>() {

    var data: MutableList<ContactData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return VHolder(view)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int = data.size

    inner class VHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val binding : ItemContactBinding = DataBindingUtil.bind(itemView)!!
        fun onBind(position: Int) {
            binding.setVariable(BR.data , data[position])
            binding.btnDeleteContactItem.setOnClickListener {
                data.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, data.size)
            }
        }
    }
}