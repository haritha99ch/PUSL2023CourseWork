package com.example.login.ViewModels

import com.example.login.ImgUrl

class Post {
    lateinit var profilePic:String
    lateinit var userName:String
    lateinit var dateTime:String
    lateinit var heading:String
    lateinit var description:String
    lateinit var postImage:String
    var likes:Int=0
    var comments:List<Comment>? = null

    var profilePicUrl:String = ""
        get() = "$ImgUrl.$profilePic"
    var postImageUrl:String=""
        get() = "$ImgUrl.$postImage"
}


