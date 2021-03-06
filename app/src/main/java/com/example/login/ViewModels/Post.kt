package com.example.login.ViewModels

import com.example.login.ImgUrl

class Post {
    var postId: Int = 0
    lateinit var profilePic:String
    lateinit var userName:String
    lateinit var dateTime:String
    lateinit var gameName:String
    lateinit var Description:String
    lateinit var postImage:String
    var currUserLiked:Boolean=false
    var likes:Int=0
    var noComments:Int=0
    var comments:List<Comment>? = null

    var profilePicUrl:String = ""
        get() = "$ImgUrl$profilePic"
    var postImageUrl:String=""
        get() = "$ImgUrl$postImage"
}


