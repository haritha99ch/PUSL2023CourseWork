package com.example.login

import android.content.Context
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.login.ViewModels.Post
import com.example.login.R
import com.example.login.R.drawable

class FeedRecyclerAdapter(
    val context: Context,
    val posts: List<Post>
) : RecyclerView.Adapter<FeedRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtUserName = itemView.findViewById<TextView>(R.id.userName)
        val txtDatetime=itemView.findViewById<TextView>(R.id.date_time)
        val imgPostImage=itemView.findViewById<ImageView>(R.id.postpic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.feed_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        with(holder) {
            txtUserName.text = post.userName
            var img=post.postImageUrl
            Log.i("GGData", "$img RecyclerView")

            imgPostImage.setImageResource(R.drawable.killer)
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}