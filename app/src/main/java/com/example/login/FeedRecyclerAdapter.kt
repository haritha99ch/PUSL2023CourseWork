package com.example.login

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.login.Data.Entities.Reactions
import com.example.login.Data.GGDbContext
import com.example.login.Session.LoginPref
import com.example.login.ViewModels.Comment
import com.example.login.ViewModels.Post
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.coroutines.*

class FeedRecyclerAdapter(
    val context: Context,
    val posts: List<Post>,
    val activity: Activity
) : RecyclerView.Adapter<FeedRecyclerAdapter.PostViewHolder>() {
    private lateinit var navControl:NavController

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtUserName = itemView.findViewById<TextView>(R.id.userName)
        val txtDatetime = itemView.findViewById<TextView>(R.id.date_time)
        val imgPostImage = itemView.findViewById<ImageView>(R.id.postpic)
        val btnLike = itemView.findViewById<ImageButton>(R.id.btnLike)
        val btnComment = itemView.findViewById<ImageButton>(R.id.imgBtnComment)



    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.feed_item, parent, false)
        navControl=Navigation.findNavController(
            activity, R.id.mainContainer
        )
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
            if (post.currUserLiked) {
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
                if (post.currUserLiked){
                    Toast.makeText(context, "You disliked this photo ", Toast.LENGTH_SHORT).show()
                    disLikeThePost(post.postId)
                    btnLike.setColorFilter(
                        ContextCompat.getColor(context, R.color.white),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
                }else{
                    Toast.makeText(context, "You liked this photo ", Toast.LENGTH_SHORT).show()
                    likeThePost(post.postId)
                    btnLike.setColorFilter(
                        ContextCompat.getColor(context, R.color.DarkPink),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
                }
            }
            btnComment.setOnClickListener {
                val postId = null
                val bundle= bundleOf(Pair("PostId", post.postId) )
                navControl.navigate(R.id.action_nav_commentSection, bundle)
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

    private fun disLikeThePost(postId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            var session = LoginPref(context)
            val user = session.getUserDetails()
                .get(LoginPref.KEY_USERNAME).toString()
            val Dao = GGDbContext.getInstance(context).gGdbDao
            Dao.disLikePost(postId, user)
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
        }
    }


    override fun getItemCount(): Int {
        return posts.size
    }



}