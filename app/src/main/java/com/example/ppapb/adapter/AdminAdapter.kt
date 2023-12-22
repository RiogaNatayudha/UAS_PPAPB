package com.example.ppapb.admin

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
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
import com.example.ppapb.item.Movie
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AdminAdapter(private val itemList : ArrayList<Movie>) : RecyclerView
.Adapter<AdminAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return itemList.size
    }

    class MyViewHolder(itemList : View) : RecyclerView.ViewHolder(itemList){
        val title : TextView = itemList.findViewById(R.id.eachItemAdminTitle)
        val image : ImageView = itemList.findViewById(R.id.eachItemAdminImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_admin,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.title.text = currentItem.judul
        // Load and display the image using Glide
        Glide.with(holder.itemView.context)
            .load(currentItem.imageUrl)
            .skipMemoryCache(true) // Skip caching in memory
            .diskCacheStrategy(DiskCacheStrategy.NONE) // Skip caching on disk
            .into(holder.image)

        holder.itemView.findViewById<ImageView>(R.id.edit_item).setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateAdmin::class.java)
            intent.putExtra("title", currentItem.judul)
            intent.putExtra("author", currentItem.pembuatdanrating) // Sesuaikan dengan properti yang ada di objek Item
            intent.putExtra("genre", currentItem.genre)   // Sesuaikan dengan properti yang ada di objek Item
            intent.putExtra("storyline", currentItem.storyline) // Sesuaikan dengan properti yang ada di objek Item
            intent.putExtra("description", currentItem.description) // Sesuaikan dengan properti yang ada di objek Item
            intent.putExtra("imgUrl", currentItem.imageUrl)
            holder.itemView.context.startActivity(intent)
        }

        holder.itemView.findViewById<ImageView>(R.id.delete_item).setOnClickListener {
            val itemToDelete = Uri.parse(itemList[position].imageUrl.toString()).lastPathSegment?.removePrefix("images/")

            // Remove the item from the list
            itemList.removeAt(position)

            // Notify the adapter of the data change
            notifyDataSetChanged()

            // Delete the corresponding data from the Realtime Database
            deleteItemFromDatabase(itemToDelete.toString())
        }

    }

    private fun deleteItemFromDatabase(imgId: String) {
        // Reference to the Firebase Storage
        val storageReference = FirebaseStorage.getInstance().getReference("images").child(imgId)

        // Delete the image from Firebase Storage
        storageReference.delete().addOnSuccessListener {
            // Image deleted successfully, now delete the corresponding data from the Realtime Database
            val database = FirebaseDatabase.getInstance().getReference("Admin")
            database.child(imgId).removeValue()
                .addOnCompleteListener {
                    // Handle success if needed
                }
                .addOnFailureListener {
                    // Handle failure if needed
                }
        }.addOnFailureListener {
            // Handle failure if the image deletion fails
            Log.e("AdminAdapter", "Error deleting image: ${it.message}")
        }
    }


}