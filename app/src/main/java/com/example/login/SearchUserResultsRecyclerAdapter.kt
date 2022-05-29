package com.example.login

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.login.Data.Entities.Followers
import com.example.login.Data.Entities.Relations.UserAccount
import com.example.login.Data.GGDbContext
import com.example.login.Data.GGdbDao
import com.example.login.Session.LoginPref
import kotlinx.coroutines.*

class SearchUserResultsRecyclerAdapter(
   val context: Context,
   val users: List<UserAccount>
) :RecyclerView.Adapter<SearchUserResultsRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val imgProfilePic=itemView.findViewById<ImageView>(R.id.imgProfilePic)
        val lblUserName=itemView.findViewById<TextView>(R.id.txtUserName)
        val lblRealName=itemView.findViewById<TextView>(R.id.txtRealName)
        val btnFollow=itemView.findViewById<Button>(R.id.btnFollow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.searchrsltusers_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user=users[position]
        with(holder){
            Glide.with(context)
                .asBitmap()
                .load(user.Account.ProfilePicUrl)
                .into(imgProfilePic)
            lblUserName.text=user.Account.UserName
            lblRealName.text="${user.User.FirstName} ${user.User.LastName}"
            GlobalScope.launch {
                val session = LoginPref(context)
                val currentUser = session.getUserDetails().get(LoginPref.KEY_USERNAME)
                val Dao= GGDbContext.getInstance(context).gGdbDao
                if (Dao.isFollowing(currentUser!!, user.Account.UserName)){
                    btnFollow.let {
                        setButtonUnfollow(it, Dao, user, currentUser)
                    }
                }else{
                    btnFollow.let {
                        setButtonFollow(it, Dao, user, currentUser)
                    }
                }
            }
        }
    }

    private fun setButtonFollow(
        it: Button,
        Dao: GGdbDao,
        user: UserAccount,
        currentUser: String?
    ) {
        it.text = "Follow"
        it.setBackgroundColor(it.getContext().getResources().getColor(R.color.lightBlue))
        it.setOnClickListener {i->
            CoroutineScope(Dispatchers.IO).launch {
                Dao.followUser(
                    Followers(
                        user.Account.UserName,
                        currentUser!!
                    )
                )
            }
            Toast.makeText(context,"You Started Following ${user.Account.UserName}",Toast.LENGTH_SHORT).show()
            setButtonUnfollow(it, Dao, user, currentUser)
        }
    }

    private fun setButtonUnfollow(
        it: Button,
        Dao: GGdbDao,
        user: UserAccount,
        currentUser: String?
    ) {
        it.text = "Unfollow"
        it.setBackgroundColor(it.getContext().getResources().getColor(R.color.white))
        it.setTextColor(it.getContext().getResources().getColor(R.color.HeavyDark))
        it.setOnClickListener {i->
            CoroutineScope(Dispatchers.IO).launch {
                Dao.unfollowUser(user.Account.UserName, currentUser!!)
            }
            Toast.makeText(context,"You Started Following ${user.Account.UserName}", Toast.LENGTH_SHORT).show()
            setButtonFollow(it, Dao, user, currentUser)
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

}
