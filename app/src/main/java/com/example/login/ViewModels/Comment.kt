package com.example.login.ViewModels

import com.example.login.ImgUrl

class Comment {
    lateinit var profilePic: String
    lateinit var userName: String
    lateinit var dateTime: String
    lateinit var comment: String

    var profilePicUrl: String = ""
        get() = "$ImgUrl.$profilePic"
}