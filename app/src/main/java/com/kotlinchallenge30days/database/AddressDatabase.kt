package com.kotlinchallenge30days.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Address::class], version = 1, exportSchema = false)
abstract class AddressDatabase : RoomDatabase() {

    abstract val addressDatabaseDao: AddressDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: AddressDatabase? = null

        fun getInstance(context: Context): AddressDatabase? {
            if (INSTANCE == null) {
                synchronized(AddressDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AddressDatabase::class.java,
                        "addressdb"
                    )
                        .allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
    }
}
