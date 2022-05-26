package com.example.login

import android.app.Activity
import android.content.Context
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
        val imgProfilePic=itemView.findViewById<ImageView>(R.id.profile_pic)
        val txtUserName = itemView.findViewById<TextView>(R.id.userName)
        val txtDatetime = itemView.findViewById<TextView>(R.id.date_time)
        val txtGameName=itemView.findViewById<TextView>(R.id.txtGameName)
        val txtDescription=itemView.findViewById<TextView>(R.id.description)
        val imgPostImage = itemView.findViewById<ImageView>(R.id.postpic)
        val txtPostLikes=itemView.findViewById<TextView>(R.id.post_likes)
        val txtComments=itemView.findViewById<TextView>(R.id.post_comments)
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
            Glide.with(context)
                .asBitmap()
                .load(post.profilePicUrl)
                .into(imgProfilePic)
            txtUserName.text = post.userName
            txtDatetime.text=post.dateTime
            txtGameName.text=post.gameName
            txtDescription.text=post.Description
            txtPostLikes.text=post.likes.toString()
            txtComments.text="${post.noComments} Comments"
            Glide.with(context)
                .asBitmap()
                .load(post.postImageUrl)
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
                    txtPostLikes.text=(txtPostLikes.text.toString().toInt()-1).toString()
                    btnLike.setColorFilter(
                        ContextCompat.getColor(context, R.color.white),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
                }else{
                    Toast.makeText(context, "You liked this photo ", Toast.LENGTH_SHORT).show()
                    likeThePost(post.postId)
                    txtPostLikes.text=(txtPostLikes.text.toString().toInt()+1).toString()
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
            var list = mutableListOf<Comment>()
            var testCom = Comment()
            testCom.userName = "Infi"
            testCom.comment = "This is a comment"
            list += testCom


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