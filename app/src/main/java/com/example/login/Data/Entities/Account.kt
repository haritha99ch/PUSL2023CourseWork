package com.example.login.Data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

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
        get() = "Url/$ProfilePic.png"
    val CoverPicUrl
        get() = "Url/$CoverPic.png"
}