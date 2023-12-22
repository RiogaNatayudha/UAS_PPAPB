package com.example.ppapb.adapter

import android.content.Intent
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.ppapb.R
import com.example.ppapb.activity.DetailActivity
import com.example.ppapb.item.Movie

class UserAdapter(private var itemList: List<Note>) : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {


    class MyViewHolder(itemList: View) : RecyclerView.ViewHolder(itemList) {

        val judul: TextView = itemList.findViewById(R.id.judul_item)
        val gambar: ImageView = itemList.findViewById(R.id.gambar_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.judul.text = currentItem.judul

        // Load and display the image using Glide
        Glide.with(holder.itemView.context)
            .load(currentItem.imageUrl)
            .skipMemoryCache(true) // Skip caching in memory
            .diskCacheStrategy(DiskCacheStrategy.NONE) // Skip caching on disk
            .into(holder.gambar)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("title", currentItem.judul)
            intent.putExtra("author", currentItem.pembuatdanrating) // Sesuaikan dengan properti yang ada di objek Item
            intent.putExtra("genre", currentItem.genre)   // Sesuaikan dengan properti yang ada di objek Item
            intent.putExtra("storyline", currentItem.storyline) // Sesuaikan dengan properti yang ada di objek Item
            intent.putExtra("description", currentItem.description) // Sesuaikan dengan properti yang ada di objek Item
            intent.putExtra("imgUrl", currentItem.imageUrl)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun updateData(newList: List<Note>) {
        itemList = newList
        notifyDataSetChanged()
    }


}