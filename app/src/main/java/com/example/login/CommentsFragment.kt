package com.example.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.login.Data.Entities.Relations.PostwAccount
import com.example.login.Data.GGDbContext
import com.example.login.Data.GGdbDao
import com.example.login.Session.LoginPref
import com.example.login.ViewModels.Comment
import com.example.login.ViewModels.Post
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

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
    private lateinit var lblPostedUserName: TextView
    private lateinit var lblDate: TextView
    private lateinit var lblDescription: TextView
    private lateinit var lblGameName: TextView
    private lateinit var imgPostPhoto: ImageView
    private lateinit var lblNoLikes: TextView
    private lateinit var commentRecyclerView: RecyclerView
    private lateinit var imgCurrUserPic: ImageView
    private lateinit var txtComment: EditText
    private lateinit var btnComment: ImageButton


    private lateinit var Post: PostwAccount
    private lateinit var post: Post

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
        val view = inflater.inflate(R.layout.fragment_comments, container, false)
        imgProfilePicture = view.findViewById(R.id.profile_pic)
        lblPostedUserName = view.findViewById(R.id.cvUserName)
        lblDate = view.findViewById(R.id.date_time)
        lblGameName = view.findViewById(R.id.txtGameName)
        lblDescription = view.findViewById(R.id.description)
        imgPostPhoto = view.findViewById(R.id.imgPostPhoto)
        lblNoLikes = view.findViewById(R.id.post_likes)
        commentRecyclerView = view.findViewById(R.id.rwCommentSection)
        imgCurrUserPic = view.findViewById(R.id.imgCurrentUserPic)
        txtComment = view.findViewById(R.id.txtComment)
        btnComment = view.findViewById(R.id.btnComment)
        btnComment.isEnabled = false

        //Session
        var session = LoginPref(requireContext())
        val currUserName = session.getUserDetails().get(LoginPref.KEY_USERNAME)


        var Comments: List<Comment> = emptyList()

        post = Post()
        val postId = arguments?.getInt("PostId")
        val Dao = GGDbContext.getInstance(requireContext()).gGdbDao
        Log.i("PostIdB", "$postId")


        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                Post = Dao.selectPostwAccount(postId!!)
            }

            val PostImage = Dao.selectPostImage(postId!!)
            post.postId = Post.Post.PostId
            post.profilePic = Post.Account.ProfilePic
            post.userName = Post.Account.UserName
            post.dateTime = Post.Post.DateTime
            post.gameName = Post.Post.Heading
            post.postImage = PostImage.ImageName
            post.likes = Dao.countPostLikes(postId!!)
            post.Description = Post.Post.Description
            var img = post.postImageUrl
            Log.i("PostIdB", "${post.userName}")

            Glide.with(requireContext())
                .asBitmap()
                .load(post.profilePicUrl)
                .into(imgProfilePicture)
            lblPostedUserName.text = post.userName
            lblDate.text = post.dateTime
            lblGameName.text = post.gameName
            lblDescription.text = post.Description
            Glide.with(requireContext())
                .asBitmap()
                .load(post.postImageUrl)
                .into(imgPostPhoto)
            lblNoLikes.text = post.likes.toString()

            //Comment Section
            updateCommentSection(Dao, postId, Comments)
            setupEventHandlers(txtComment, btnComment, postId!!, currUserName, Dao, Comments)

        }

        return view
    }

    private suspend fun updateCommentSection(
        Dao: GGdbDao,
        postId: Int?,
        Comments: List<Comment>
    ) {
        var Comments1 = Comments
        val comments = Dao.selectCommentswAccount(postId!!)

        comments.forEach {
            val newComment = Comment()
            newComment.profilePic = it.Account.ProfilePic
            newComment.userName = it.Account.UserName
            newComment.comment = it.Comment.Comment
            Comments1 += newComment

        }
        val adapter = CommentRecyclerAdapter(requireContext(), comments)
        Log.i("Comments", "$comments")
        commentRecyclerView.adapter = adapter
    }

    private fun setupEventHandlers(
        txtComment: EditText,
        btnComment: ImageButton,
        postId: Int?,
        currUserName: String?,
        Dao: GGdbDao,
        Comments: List<Comment>,
    ) {
        txtComment.addTextChangedListener {
            btnComment.isEnabled = !txtComment.text.isNullOrEmpty()
        }
        btnComment.setOnClickListener {
            lifecycleScope.launch {
                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val currentDate = sdf.format(Date())
                GGDbContext.getInstance(requireContext()).gGdbDao
                    .NewComment(
                        com.example.login.Data.Entities.Comment(
                            0,
                            postId!!,
                            currUserName!!,
                            currentDate.toString(),
                            txtComment.text.toString()
                        )
                    )
                updateCommentSection(Dao, postId, Comments)
            }
            txtComment.text.clear()
            txtComment.clearFocus()
        }
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
        fun newInstance(param1: String, param2: String) =
            CommentsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}