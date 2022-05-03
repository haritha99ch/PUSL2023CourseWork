package com.example.login.Data.Entities.Relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.login.Data.Entities.Account
import com.example.login.Data.Entities.Comment

data class CommentwAccount(
    @Embedded
    var Comment:Comment,
    @Relation(
        parentColumn = "UserName",
        entityColumn = "UserName"
    )
    var Account:Account
) {
}