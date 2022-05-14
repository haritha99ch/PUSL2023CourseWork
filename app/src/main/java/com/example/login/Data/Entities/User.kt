package com.example.login.Data.Entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.util.*

@Entity(
    tableName = "User",
    foreignKeys = [
        ForeignKey(
            entity = Account::class,
            parentColumns = ["UserName"],
            childColumns = ["UserName"]
        )
    ]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    var UserId: Int,
    var UserName:String,
    var FirstName: String,
    var LastName: String,
    var Gender: String,
    var Age: Int
) {}