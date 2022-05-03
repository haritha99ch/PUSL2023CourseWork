package com.example.login.Data.Entities.Relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.login.Data.Entities.Account
import com.example.login.Data.Entities.Post

data class PostsAccount(
    @Embedded
    var Account:Account,
    @Relation(
        parentColumn = "UserName",
        entityColumn = "UserName"
    )
    var Posts:List<Post>
) {
}