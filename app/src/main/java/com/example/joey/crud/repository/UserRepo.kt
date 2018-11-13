package com.example.joey.crud.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.joey.crud.data.User
import com.example.joey.crud.data.UserDao

class UserRepo(private val userDao: UserDao) {
    val allUsers: LiveData<List<User>> = userDao.getAllUsers()

    @WorkerThread
    fun insert(user: User) {
        userDao.insert(user)
    }
}
