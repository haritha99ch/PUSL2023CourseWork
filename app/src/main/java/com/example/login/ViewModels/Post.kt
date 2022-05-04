package com.example.login.ViewModels

class Post {
    lateinit var profilePic:String
    lateinit var userName:String
    lateinit var dateTime:String
    lateinit var heading:String
    lateinit var description:String
    lateinit var image:String
    var likes:Int=0
    lateinit var comments:List<comment>
}

class comment {
    lateinit var profilePic:String
    lateinit var userName:String
    lateinit var dateTime:String
    lateinit var comment:String
}
