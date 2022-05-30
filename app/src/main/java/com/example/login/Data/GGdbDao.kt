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

    @Insert
    suspend fun NewComment(comment: Comment)

    //Update Profile Picture
    @Query("Update Account Set ProfilePic=:image Where UserName=:userName")
    suspend fun SetProfilePicture(userName: String, image: String)

    @Query("Select ProfilePic From Account Where UserName=:userName")
    suspend fun GetProfilePicture(userName: String):String

    //Count
    @Query("Select Count() From Reactions Where PostId=:postId")
    suspend fun countPostLikes(postId: Int):Int

    @Query("Select Count() From Comment Where PostId=:postId")
    suspend fun countPostComments(postId: Int):Int

    @Query("Select Count() From Followers Where UserName=:userName")
    suspend fun countFollowers(userName:String):Int

    @Query("Select Count() From Followers Where Follower=:userName")
    suspend fun countFollowing(userName:String):Int

    @Query("Select Count() From Reactions As R Join Post As P On R.PostId=P.PostId Join Account As A On P.UserName=A.UserName Where A.UserName=:userName")
    suspend fun countTotalLikes(userName: String):Int

    //Validations
    @Query("Select Exists(Select * From Reactions Where PostId=:postId And UserName=:userName)")
    suspend fun isLikedPost(postId: Int, userName: String):Boolean

    @Query("Select Exists(Select * From Followers Where UserName=:follower And Follower=:userName)")
    suspend fun isFollowing(userName: String, follower:String):Boolean

    //Following
    @Insert
    suspend fun followUser(follower:Followers)

    @Query("Delete From Followers Where UserName=:userName And Follower=:follower")
    suspend fun unfollowUser(userName: String, follower: String)


    @Query("Delete From Reactions Where PostId=:postId And UserName=:userName")
    suspend fun disLikePost(postId: Int, userName: String)



    @Query("Select * From Comment Where PostId=:postId Order By CommentId Desc")
    suspend fun selectCommentswAccount(postId:Int):List<CommentwAccount>

    @Query("Select * From Image Where PostId=:postId")
    suspend fun selectPostImage(postId: Int):Image

    @Query("Select * From Post Order By PostId Desc")
    suspend fun selectPostswAccount():List<PostwAccount>

    @Query("Select * From Post Where UserName=:userName Order By RANDOM()")
    suspend fun selectUserPostswAccount(userName: String):List<PostwAccount>

    @Query("Select * From Post")
    suspend fun selectPostCommentsAccount():List<PostwComments>

    @Query("Select * From Post Where PostId=:postId")
    suspend fun selectPostwAccount(postId: Int):PostwAccount

    @Query("Select * From Account Where UserName=:userName")
    suspend fun selectUserAccount(userName:String): UserAccount

    @Query("Select * From Account")
    suspend fun selectUserAccounts():List<UserAccount>
}