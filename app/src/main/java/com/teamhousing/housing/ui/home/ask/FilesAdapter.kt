package com.teamhousing.housing.ui.home.ask

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.teamhousing.housing.R
import com.teamhousing.housing.databinding.ItemFileBinding

class FilesAdapter : RecyclerView.Adapter<FilesAdapter.VHolder>() {
    var uriData = mutableListOf<Uri>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_file, parent, false)
        return VHolder(view)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.onBindForURI(uriData[position])

        holder.binding.btnPictureDelete.setOnClickListener {
            uriData.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,uriData.size)
        }

    }
    override fun getItemCount(): Int = uriData.size

    inner class VHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val binding : ItemFileBinding = DataBindingUtil.bind(itemView)!!

        fun onBindForURI(uri: Uri) {
            binding.ivPictureFile.setImageURI(uri)
        }
    }
}