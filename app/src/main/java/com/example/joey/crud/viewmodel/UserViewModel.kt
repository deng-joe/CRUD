package com.example.joey.crud.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.joey.crud.data.User
import com.example.joey.crud.data.UserDatabase
import com.example.joey.crud.repository.UserRepo
import kotlinx.coroutines.*
import kotlinx.coroutines.android.Main
import kotlin.coroutines.CoroutineContext

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repo: UserRepo
    val allUsers: LiveData<List<User>>

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

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
