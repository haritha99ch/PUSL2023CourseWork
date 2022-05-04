package com.example.login.Data.Entities.Relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.login.Data.Entities.Account
import com.example.login.Data.Entities.Image
import com.example.login.Data.Entities.Post

data class PostwAccount(
    @Embedded
    var Post:Post,
    @Relation(
        parentColumn = "UserName",
        entityColumn = "UserName",
    )
    var Account:Account
) {
}