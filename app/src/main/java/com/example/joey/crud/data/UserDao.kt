package com.example.joey.crud.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert(onConflict = REPLACE)
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * FROM users ORDER BY ID")
    fun getAllUsers(): LiveData<List<User>>

    @Query("DELETE FROM users")
    fun deleteAll()
}
