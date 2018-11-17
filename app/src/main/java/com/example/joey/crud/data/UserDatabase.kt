package com.example.joey.crud.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            val temp = INSTANCE
            if (temp != null) {
                return temp
            }

            synchronized(this) {
                val obj = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "students_db"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = obj
                return obj
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
