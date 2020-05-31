package com.kotlinchallenge30days.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Address(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "phone")
    var phone: String = "",
    @ColumnInfo(name = "email")
    var email: String = "",
    @ColumnInfo(name = "society")
    var society: String = "",
    @ColumnInfo(name = "landmark")
    var landmark: String = "",

    @ColumnInfo(name = "area")
    var area: String = "",

    @ColumnInfo(name = "city")
    var city: String = "",

    @ColumnInfo(name = "state")
    var state: String = "",

    @ColumnInfo(name = "pincode")
    var pincode: String = ""

) : Serializable