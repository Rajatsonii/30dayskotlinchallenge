package com.kotlinchallenge30days.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AddressDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(night: Address)

    @Query("SELECT * FROM address")
    fun getAllAddress(): List<Address>

    @Query("DELETE FROM address WHERE id=:id")
    fun removeAddress(id: Long)
}
