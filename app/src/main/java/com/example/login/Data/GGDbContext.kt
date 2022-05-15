package com.example.login.Data

import android.content.Context
import android.provider.MediaStore
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.login.Data.Entities.*

@Database(
    entities = [
        User::class,
        Account::class,
        Post::class,
        Comment::class,
        Image::class,
        Followers::class,
        Reactions::class
    ],
    version = 2
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