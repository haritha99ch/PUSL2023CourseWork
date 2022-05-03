package com.example.login.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.login.Data.Entities.Account
import com.example.login.Data.Entities.Comment
import com.example.login.Data.Entities.Post
import com.example.login.Data.Entities.User

@Database(
    entities = [
        User::class,
        Account::class,
        Post::class,
        Comment::class
    ],
    version = 1
)
abstract class GGDbContext:RoomDatabase() {
    abstract val gGdbDao:GGdbDao

    companion object{
        @Volatile
        private var INSTANCE:GGDbContext?=null
        fun getInstance(context: Context):GGDbContext{
            synchronized(this){
                return INSTANCE?: Room.databaseBuilder(
                    context.applicationContext,
                    GGDbContext::class.java,
                    "GGdb"
                ).build().also {
                    INSTANCE=it
                }
            }
        }
    }

}