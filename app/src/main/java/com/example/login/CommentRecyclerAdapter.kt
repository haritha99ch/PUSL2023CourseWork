package com.example.login

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.login.ViewModels.Comment

class CommentRecyclerAdapter(
    val context: Context,
    val comments:List<Comment>?
) :RecyclerView.Adapter<CommentRecyclerAdapter.CommentViewHolder>(){
    inner class CommentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtUserName=itemView.findViewById<TextView>(R.id.txtUserName)
        val txtComment=itemView.findViewById<TextView>(R.id.txtComment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.feed_item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment= comments?.get(position)
        with(holder){
            txtUserName.text=comment!!.userName
            txtComment.text=comment!!.comment
        }
    }

    override fun getItemCount(): Int {
        return comments!!.size
    }


}