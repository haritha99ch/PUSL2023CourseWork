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
    suspend fun isLikedPost(postId: Int, userName: String):Boolean

    @Query("Select Count() From Followers Where UserName=:userName")
    suspend fun countFollowers(userName:String):Int

    @Query("Select Count() From Followers Where Follower=:userName")
    suspend fun countFollowing(userName:String):Int

    @Query("Select * From Comment Where PostId=:postId")
    suspend fun selectCommentswAccount(postId:Int):List<CommentwAccount>

    @Query("Select * From Image Where PostId=:postId")
    suspend fun selectPostImage(postId: Int):Image

    @Query("Select * From Post Order By RANDOM()")
    suspend fun selectPostswAccount():List<PostwAccount>

    @Query("Select * From Post Where UserName=:userName Order By RANDOM()")
    suspend fun selectUserPostswAccount(userName: String):List<PostwAccount>

    @Query("Select * From Post")
    suspend fun selectPostCommentsAccount():List<PostwComments>

    @Query("Select * From Account")
    suspend fun selectPostAccount():List<PostsAccount>

    @Query("Select * From Account Where UserName=:userName")
    suspend fun selectUserAccount(userName:String): UserAccount
}