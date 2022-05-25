package com.example.login

import android.graphics.Picture
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.login.Data.Entities.Relations.PostwAccount
import com.example.login.Data.GGDbContext
import com.example.login.ViewModels.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CommentsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CommentsFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var imgProfilePicture: ImageView
    private lateinit var lblPostedUserName:TextView
    private lateinit var lblDate:TextView
    private lateinit var lblDescription:TextView
    private lateinit var lblGameName:TextView
    private lateinit var imgPostPhoto:ImageView
    private lateinit var lblNoLikes:TextView
    private lateinit var commentRecyclerView: RecyclerView

    private lateinit var Post:PostwAccount
    private lateinit var post:Post

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
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_comments, container, false)
        imgPostPhoto=view.findViewById(R.id.imgPostPhoto)
        lblPostedUserName=view.findViewById(R.id.cvUserName)
        post=Post()
        val postId=arguments?.getInt("PostId")
        val Dao=GGDbContext.getInstance(requireContext()).gGdbDao
        Log.i("PostIdB", "$postId")
        lifecycleScope.launch {
            Post= withContext(Dispatchers.IO){
                Dao.selectPostwAccount(postId!!)
            }
            val PostImage= withContext(Dispatchers.IO){
                Dao.selectPostImage(postId!!)
            }
            withContext(Dispatchers.IO){
                post.postId=Post.Post.PostId
                post.profilePic = Post.Account.ProfilePic
                post.userName = Post.Account.UserName
                post.dateTime = Post.Post.DateTime
                post.heading = Post.Post.Heading
                post.postImage = PostImage.ImageName
                post.likes = 2
                post.heading="Elo"
                post.postImageUrl="sdf"
                post.profilePicUrl="sdf"
                var img = post.postImageUrl
                Log.i("PostIdB", "${post.userName}")

            }
            withContext(Dispatchers.Main){
                lblPostedUserName.text=post.userName

            }


        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CommentsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1:String, param2:String) =
            CommentsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}