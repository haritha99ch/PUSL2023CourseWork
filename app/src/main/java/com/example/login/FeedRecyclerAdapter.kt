package com.example.login

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.CorrectionInfo
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.login.Data.Entities.Reactions
import com.example.login.Data.GGDbContext
import com.example.login.Session.LoginPref
import com.example.login.ViewModels.Comment
import com.example.login.ViewModels.Post
import kotlinx.coroutines.*

class FeedRecyclerAdapter(
    val context: Context,
    val posts: List<Post>
) : RecyclerView.Adapter<FeedRecyclerAdapter.PostViewHolder>() {


    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtUserName = itemView.findViewById<TextView>(R.id.userName)
        val txtDatetime = itemView.findViewById<TextView>(R.id.date_time)
        val imgPostImage = itemView.findViewById<ImageView>(R.id.postpic)
        val btnLike = itemView.findViewById<ImageButton>(R.id.btnLike)

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
            var img = post.postImageUrl
            Glide.with(context)
                .asBitmap()
                .load(img)
                .into(imgPostImage)
            if (false) {
                btnLike.setColorFilter(
                    ContextCompat.getColor(context, R.color.DarkPink),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
            } else {
                btnLike.setColorFilter(
                    ContextCompat.getColor(context, R.color.white),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
            }
            btnLike.setOnClickListener {
                Toast.makeText(context, "You liked this photo ", Toast.LENGTH_SHORT).show()
                likeThePost(post.postId)
                btnLike.setColorFilter(
                    ContextCompat.getColor(context, R.color.DarkPink),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
            }
            Log.i("GGData", "$img RecyclerView")
            var list = mutableListOf<Comment>()
            var testCom = Comment()
            testCom.userName = "Infi"
            testCom.comment = "This is a comment"
            list += testCom


            val commentRecyclerAdapter = CommentRecyclerAdapter(context, list)
        }
    }


    private fun likeThePost(postId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            var session = LoginPref(context)
            val user = session.getUserDetails()
            val Dao = GGDbContext.getInstance(context).gGdbDao
            Dao.NewReaction(
                Reactions(
                    postId,
                    user.get(LoginPref.KEY_USERNAME).toString()
                )
            )
            this.cancel()
        }
    }


    override fun getItemCount(): Int {
        return posts.size
    }
}