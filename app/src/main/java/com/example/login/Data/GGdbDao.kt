package com.example.login.Data

import androidx.room.Dao
import androidx.room.Query
import com.example.login.Data.Entities.Relations.UserAccount

@Dao
interface GGdbDao {

    @Query("Select * From Account")
    fun sellectUserAccount(): List<UserAccount>
}