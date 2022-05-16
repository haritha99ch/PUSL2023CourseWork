package com.example.login.Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.login.Data.Entities.*
import com.example.login.Data.Entities.Relations.*

@Dao
interface GGdbDao {

    @Insert
    suspend fun NewUser(user: User)

    @Insert
    suspend fun NewAccount(account: Account)

    @Insert
    suspend fun NewPost(post: Post):Long

    @Insert
    suspend fun NewImage(image: Image)

    @Insert
    suspend fun NewReaction(reactions: Reactions)

    @Query("Select Exists(Select * From Reactions Where PostId=:postId And UserName=:userName)")
    fun isLikedPost(postId: Int, userName: String):Boolean

    @Query("Select Count() From Followers Where UserName=:userName")
    fun countFollowers(userName:String):Int

    @Query("Select Count() From Followers Where Follower=:userName")
    fun countFollowing(userName:String):Int

    @Query("Select * From Comment Where PostId=:postId")
    fun selectCommentswAccount(postId:Int):List<CommentwAccount>

    @Query("Select * From Image Where PostId=:postId")
    fun selectPostImage(postId: Int):Image

    @Query("Select * From Post Order By RANDOM()")
    fun selectPostswAccount():List<PostwAccount>

    @Query("Select * From Post")
    fun selectPostCommentsAccount():List<PostwComments>

    @Query("Select * From Account")
    fun selectPostAccount():List<PostsAccount>

    @Query("Select * From Account Where UserName=:userName")
    fun selectUserAccount(userName:String): UserAccount
}