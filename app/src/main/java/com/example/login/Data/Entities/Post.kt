package com.example.login.Data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "Post")
data class Post(
    @PrimaryKey(autoGenerate = true)
    var PostId:Int,
    var DateTime:LocalDateTime,
    var Heading:String,
    var Description:String,
) {
}