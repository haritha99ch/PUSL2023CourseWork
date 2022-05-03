package com.example.login.Data.Entities.Relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.login.Data.Entities.Account
import com.example.login.Data.Entities.Comment
import com.example.login.Data.Entities.Post

data class PostwComments(
    @Embedded
    var Post:Post,
    @Relation(
        parentColumn = "PostId",
        entityColumn = "PostId",
    )
    var Comments:List<Comment>
) {
}