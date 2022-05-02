package com.example.login.Data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Image")
data class Image(
    @PrimaryKey(autoGenerate = true)
    var ImageId:Int,
    var ImageName:String
) {
}