package com.example.login.Data.Entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "Post",
    foreignKeys = [
        ForeignKey(
            entity = Account::class,
            parentColumns = ["UserName"],
            childColumns = ["UserName"]
        )
    ]
)
data class Post(
    @PrimaryKey(autoGenerate = true)
    var PostId: Int,
    var UserName:String,
    var DateTime: String,
    var Heading: String,
    var Description: String,
) {
}