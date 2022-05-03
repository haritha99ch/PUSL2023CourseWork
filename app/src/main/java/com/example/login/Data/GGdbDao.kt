package com.example.login.Data

import androidx.room.Dao
import androidx.room.Query
import com.example.login.Data.Entities.Relations.*

@Dao
interface GGdbDao {

    @Query("Select * From Comment Where PostId=:postId")
    fun selectCommentswAccount(postId:Int):List<CommentwAccount>

    @Query("Select * From Post")
    fun selectPostswAccount():List<PostwAccount>

    @Query("Select * From Post")
    fun selectPostCommentsAccount():List<PostwComments>

    @Query("Select * From Account")
    fun selectPostAccount():List<PostsAccount>

    @Query("Select * From Account")
    fun selectUserAccount(): List<UserAccount>
}