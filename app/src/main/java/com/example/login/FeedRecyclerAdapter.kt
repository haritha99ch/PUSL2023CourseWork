package com.example.login

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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
        val rwComments=itemView.findViewById<RecyclerView>(R.id.commentView)
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
            Log.i("GGData", "${post.comments} RecyclerView")
            var list= mutableListOf<Comment>()
            var testCom=Comment()
            testCom.userName="Infi"
            testCom.comment="This is a comment"
            list+=testCom

            imgPostImage.setImageResource(R.drawable.killer)
            val commentRecyclerAdapter=CommentRecyclerAdapter(context, list)
            rwComments.adapter=commentRecyclerAdapter
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}