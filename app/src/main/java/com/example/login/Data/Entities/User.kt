package com.example.login.Data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.util.*

@Entity(tableName = "User")
data class User(
    @PrimaryKey(autoGenerate = true)
    var UserId:Int,
    var FirstName:String,
    var LastName:String,
    var Gender:String,
    var DOB:String,
    var Age:Int
) {}