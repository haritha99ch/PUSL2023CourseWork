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
import com.example.login.Data.Entities.Relations.CommentwAccount
import com.example.login.ViewModels.Comment

class CommentRecyclerAdapter(
    val context: Context,
    private val comments: List<CommentwAccount>
) :RecyclerView.Adapter<CommentRecyclerAdapter.CommentViewHolder>(){
    inner class CommentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtUserName=itemView.findViewById<TextView>(R.id.txtRWUserName)
        val txtComment=itemView.findViewById<TextView>(R.id.txtRWComment)
        val imgProfilePic=itemView.findViewById<ImageView>(R.id.imguserPic)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.comment_item, parent, false)
        Log.i("Comments", "onCreateViewHolder $comments")

        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment= comments[position]
        with(holder){
            txtUserName.text= comment.Account.UserName
            txtComment.text= comment.Comment.Comment
            Log.i("Comments", "$comments")

            Glide.with(context)
                .asBitmap()
                .load(comment.Account.ProfilePicUrl)
                .into(imgProfilePic)

        }
    }

    override fun getItemCount(): Int {
        return comments.size
    }


}