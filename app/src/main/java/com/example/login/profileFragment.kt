package com.example.login

import android.content.Intent
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.database.getStringOrNull
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
    lateinit var imgBtnProfilePic:ImageButton
    lateinit var recyclerView:RecyclerView
    private lateinit var imagefile:String
    private lateinit var lblNoFllowers:TextView
    private lateinit var lblNoFllowing:TextView
    private lateinit var lblNoLikes:TextView
    lateinit var session:LoginPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    companion object {
        val IMAGE_REQUEST_CODE = 100
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val session = LoginPref(requireContext())
        val user = session.getUserDetails().get(LoginPref.KEY_USERNAME)
        val view=inflater.inflate(R.layout.fragment_profile, container, false)
        recyclerView=view.findViewById(R.id.feedView)
        imgBtnProfilePic=view.findViewById(R.id.profilePic)
        lblNoFllowers=view.findViewById(R.id.no_of_followers)
        lblNoFllowing=view.findViewById(R.id.no_of_following)
        lblNoLikes=view.findViewById(R.id.no_of_likes)
        val btnLogOut=view.findViewById<Button>(R.id.logout)

        val Posts = mutableListOf<Post>()
        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                val Dao = GGDbContext.getInstance(requireContext()).gGdbDao
                val posts = Dao.selectUserPostswAccount(user.toString())
                Log.i("GGData", "$posts CoroutineScope \n")

                lblNoFllowers.text=Dao.countFollowers(user!!).toString()
                lblNoFllowing.text=Dao.countFollowing(user).toString()
                lblNoLikes.text=Dao.countTotalLikes(user).toString()

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
        val userName=view.findViewById<TextView>(R.id.accUserName)
        userName.text=user
        var profilePic=view.findViewById<ImageView>(R.id.profilePic)
        lifecycleScope.launch {
            val Dao = GGDbContext.getInstance(requireContext()).gGdbDao
            val session = LoginPref(requireContext())
            val user = session.getUserDetails().get(LoginPref.KEY_USERNAME)
            Glide.with(requireContext())
                .asBitmap()
                .load("$ImgUrl${Dao.GetProfilePicture(user!!)}")
                .into(imgBtnProfilePic)
            this.cancel()
        }
        Log.i("Gender", "${user}")

        btnLogOut.setOnClickListener{
            session.LogoutUser()
            Log.i("Gender", "${user} elo")
        }
        imgBtnProfilePic.setOnClickListener {
            pickImageGallery()
        }
        // Inflate the layout for this fragment
        return view
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, AddNewPost.IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == AddNewPost.IMAGE_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK){
            data?.data.let {returnUri->
                val resolver = requireActivity().contentResolver
                resolver.query(returnUri!!, null, null, null, null)
            }?.use {
                it.moveToFirst()
                val nameIndex=it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val res= it.getStringOrNull(nameIndex)
                Log.i("SelectedFile", "$res")
                imagefile= res.toString()
            }
            Glide.with(this)
                .asBitmap()
                .load("/storage/emulated/0/Download/$imagefile")
                .into(imgBtnProfilePic)
            lifecycleScope.launch {
                val Dao = GGDbContext.getInstance(requireContext()).gGdbDao
                var session = LoginPref(requireContext())
                val user = session.getUserDetails()
                Dao.SetProfilePicture(user.get(LoginPref.KEY_USERNAME)!!, imagefile)
            }
        }
    }

}