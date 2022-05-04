package com.example.login.Data

import androidx.room.Dao
import androidx.room.Query
import com.example.login.Data.Entities.Image
import com.example.login.Data.Entities.Relations.*

@Dao
interface GGdbDao {

    @Query("Select Count() From Followers Where UserName=:userName")
    fun countFollowers(userName:String):Int

    @Query("Select Count() From Followers Where Follower=:userName")
    fun countFollowing(userName:String):Int

    @Query("Select * From Comment Where PostId=:postId")
    fun selectCommentswAccount(postId:Int):List<CommentwAccount>

    @Query("Select * From Image Where PostId=:postId")
    fun selectPostImage(postId: Int):Image

    @Query("Select * From Post")
    fun selectPostswAccount():List<PostwAccount>

    @Query("Select * From Post")
    fun selectPostCommentsAccount():List<PostwComments>

    @Query("Select * From Account")
    fun selectPostAccount():List<PostsAccount>

    @Query("Select * From Account")
    fun selectUserAccount(): List<UserAccount>
}