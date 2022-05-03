package com.example.login.Data.Entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Image",
    foreignKeys = [
        ForeignKey(
            entity = Post::class,
            parentColumns = ["PostId"],
            childColumns = ["PostId"]
        )
    ]
)
data class Image(
    @PrimaryKey(autoGenerate = true)
    var ImageId:Int,
    var PostId: Int?,
    var ImageName:String
) {
}