package com.example.joey.crud.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.joey.crud.data.User
import com.example.joey.crud.data.UserDatabase
import com.example.joey.crud.repository.UserRepo
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repo: UserRepo
    val allUsers: LiveData<List<User>>  // Cache a copy of users

    init {
        val usersDao = UserDatabase.getUserDatabase(application).userDao()
        repo = UserRepo(usersDao)
        allUsers = repo.allUsers
    }

    private var job = Job()
    private val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    fun insert(user: User) = scope.launch(Dispatchers.IO) {
        repo.insert(user)
    }

    fun update(user: User) = scope.launch(Dispatchers.IO) {
        repo.update(user)
    }

    fun delete(user: User) = scope.launch(Dispatchers.Default) {
        repo.delete(user)
    }

    fun deleteAll() = scope.launch(Dispatchers.Default) {
        repo.deleteAll()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
