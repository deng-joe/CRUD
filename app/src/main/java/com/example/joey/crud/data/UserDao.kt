package com.example.joey.crud.data

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.*

@Dao
interface UserDao {
    @Insert(onConflict = REPLACE)
    fun insert(user: User)

    @Update(onConflict = ABORT)
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM users ORDER BY ID")
    fun getAllUsers(): LiveData<List<User>>

    @Query("DELETE FROM users")
    fun deleteAll()
}
