package com.example.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.login.Data.GGDbContext
import com.example.login.ViewModels.Comment
import com.example.login.ViewModels.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [feedsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class feedsFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var posts: List<Post>
    private lateinit var recyclerView: RecyclerView

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
        val view=inflater.inflate(R.layout.fragment_feeds, container, false)
        recyclerView=view.findViewById(R.id.feedView)
        var Posts = mutableListOf<Post>()
        CoroutineScope(Dispatchers.IO).launch {
            val Dao = GGDbContext.getInstance(requireContext()).gGdbDao
            val posts = Dao.selectPostswAccount()
            Log.i("GGData", "$posts CoroutineScope \n")

            posts.forEach { i ->

                val PostImage = Dao.selectPostImage(i.Post.PostId)
                val PostComments = Dao.selectCommentswAccount(i.Post.PostId)
                val Post=Post()
                Post.profilePic = i.Account.ProfilePic
                Post.userName = i.Account.UserName
                Post.dateTime = i.Post.DateTime
                Post.heading = i.Post.Heading
                Post.postImage = PostImage.ImageName
                Post.likes = 2
                Post.description="Elo"
                Post.postImageUrl="sdf"
                Post.profilePicUrl="sdf"
                PostComments.forEach { j ->
                    var Comment= Comment()
                    Comment.profilePic = j.Account.ProfilePic
                    Comment.userName = j.Account.UserName
                    Comment.dateTime = j.Comment.DateTime
                    Comment.comment = j.Comment.Comment
                    Post.comments += Comment
                }
                Posts.add(Post)
                Log.i("GGData", "$i itterate \n")

            }
        }
        Log.i("GGData", "$Posts gotposts")

        val adapter=FeedRecyclerAdapter(requireContext(), Posts)
        recyclerView.adapter=adapter
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
         * @return A new instance of fragment feedsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            feedsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}