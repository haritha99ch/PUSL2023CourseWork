package com.example.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.login.Data.GGDbContext
import com.example.login.Session.LoginPref
import com.example.login.ViewModels.Comment
import com.example.login.ViewModels.Post
import kotlinx.coroutines.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [profileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class profileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var recyclerView:RecyclerView
    lateinit var session:LoginPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var session = LoginPref(requireContext())
        val user = session.getUserDetails()
        val view=inflater.inflate(R.layout.fragment_profile, container, false)
        recyclerView=view.findViewById(R.id.feedView)
        var Posts = mutableListOf<Post>()
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                val Dao = GGDbContext.getInstance(requireContext()).gGdbDao
                val posts = Dao.selectUserPostswAccount(user.get(LoginPref.KEY_USERNAME).toString())
                Log.i("GGData", "$posts CoroutineScope \n")

                posts.forEach { i ->

                    val PostImage = Dao.selectPostImage(i.Post.PostId)
                    val PostComments = Dao.selectCommentswAccount(i.Post.PostId)
                    val Post= Post()
                    Post.postId=i.Post.PostId
                    Post.profilePic = i.Account.ProfilePic
                    Post.userName = i.Account.UserName
                    Post.dateTime = i.Post.DateTime
                    Post.gameName = i.Post.Heading
                    Post.postImage = PostImage.ImageName
                    Post.likes = 2
                    Post.Description=i.Post.Description
                    Post.postImageUrl=""
                    Post.profilePicUrl="sdf"

                    PostComments.forEach {
                        val Comment= Comment()
                        Comment.userName=it.Comment.UserName
                        Comment.comment=it.Comment.Comment
                        Post.comments = Post.comments?.plus(Comment)
                    }
                    Posts.add(Post)
                    Log.i("GGData", "$PostComments itterate \n")

                }
                Log.i("GGData", "$Posts gotposts")
                val adapter=FeedRecyclerAdapter(requireContext(), Posts, requireActivity())
                recyclerView.adapter=adapter

                this.cancel()
            }
        }
        var btnLogOut=view.findViewById<Button>(R.id.logout)
        var userName=view.findViewById<TextView>(R.id.accUserName)
        userName.text=user.get(LoginPref.KEY_USERNAME)
        var profilePic=view.findViewById<ImageView>(R.id.profilePic)
        CoroutineScope(Dispatchers.IO).launch {
            val Dao = GGDbContext.getInstance(requireContext()).gGdbDao
            val userAccount=Dao.selectUserAccount(userName.text.toString())
            val imageResource = userAccount.Account.ProfilePicUrl
            profilePic.setImageResource(R.drawable.profiledefault)
            Log.i("Gender", "${userAccount.Account.ProfilePicUrl}")
            this.cancel()
        }
        Log.i("Gender", "${user.get(LoginPref.KEY_USERNAME)}")

        btnLogOut.setOnClickListener{
            session.LogoutUser()
            Log.i("Gender", "${user.get(LoginPref.KEY_USERNAME)} elo")
        }

        // Inflate the layout for this fragment
        return view


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment profileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            profileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}