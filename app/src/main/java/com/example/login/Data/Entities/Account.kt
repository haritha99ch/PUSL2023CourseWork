package com.example.login.Data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.login.ImgUrl

@Entity(tableName = "Account")
data class Account(
    @PrimaryKey
    var UserName:String,
    var Email:String,
    var Password:String,
    var ProfilePic:String,
    var CoverPic:String
){
    val ProfilePicUrl
        get() = "$ImgUrl$ProfilePic"
    val CoverPicUrl
        get() = "$ImgUrl$CoverPic"
}