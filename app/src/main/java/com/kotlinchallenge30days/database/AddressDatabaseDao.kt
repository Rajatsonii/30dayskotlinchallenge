package com.kotlinchallenge30days.database

import androidx.room.*

@Dao
interface AddressDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(address: Address)

    @Update
    fun update(address: Address)

    @Query("SELECT * FROM address")
    fun getAllAddress(): List<Address>

    @Query("DELETE FROM address WHERE id=:id")
    fun removeAddress(id: Long)
}
