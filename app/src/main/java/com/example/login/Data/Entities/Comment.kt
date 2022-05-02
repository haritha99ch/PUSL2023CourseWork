package com.example.login.Data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "Comment")
data class Comment(
    @PrimaryKey(autoGenerate = true)
    var CommentId:Int,
    var DateTime:LocalDateTime,
    var Comment:String
) {
}