package com.example.login.Data.Entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["PostId", "UserName"],
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
data class Reactions(
    var PostId:Int,
    var UserName:String
) {
}