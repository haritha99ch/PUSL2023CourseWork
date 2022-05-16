package com.example.login

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.login.ViewModels.Comment
import com.example.login.ViewModels.Post

class FeedRecyclerAdapter(
    val context: Context,
    val posts: List<Post>
) : RecyclerView.Adapter<FeedRecyclerAdapter.PostViewHolder>() {
    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtUserName = itemView.findViewById<TextView>(R.id.userName)
        val txtDatetime=itemView.findViewById<TextView>(R.id.date_time)
        val imgPostImage=itemView.findViewById<ImageView>(R.id.postpic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.feed_item, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        with(holder) {
            txtUserName.text = post.userName
            var img=post.postImageUrl
            Glide.with(context)
                .asBitmap()
                .load(img)
                .into(imgPostImage)
            Log.i("GGData", "$img RecyclerView")
            var list= mutableListOf<Comment>()
            var testCom=Comment()
            testCom.userName="Infi"
            testCom.comment="This is a comment"
            list+=testCom


            val commentRecyclerAdapter=CommentRecyclerAdapter(context, list)
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}