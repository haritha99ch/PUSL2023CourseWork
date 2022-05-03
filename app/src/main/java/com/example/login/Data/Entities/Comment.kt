package com.example.login.Data.Entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "Comment",
    foreignKeys = [
        ForeignKey(
            entity = Post::class,
            parentColumns = ["PostId"],
            childColumns = ["PostId"]
        ),
        ForeignKey(
            entity = Account::class,
            parentColumns = ["UserName"],
            childColumns = ["UserName"]
        )
    ]
)
data class Comment(
    @PrimaryKey(autoGenerate = true)
    var CommentId:Int,
    var PostId:Int,
    var UserName:String,
    var DateTime:String,
    var Comment:String
) {
}