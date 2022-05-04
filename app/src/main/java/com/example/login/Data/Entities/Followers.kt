package com.example.login.Data.Entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["UserName", "Follower"],
    foreignKeys = [
        ForeignKey(
            entity = Account::class,
            parentColumns = ["UserName"],
            childColumns = ["UserName"]
        ),
        ForeignKey(
            entity = Account::class,
            parentColumns = ["UserName"],
            childColumns = ["Follower"]
        )
    ]
)
data class Followers(
    var UserName:String,
    var Follower:String,
) {
}