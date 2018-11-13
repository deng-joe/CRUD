package com.example.joey.crud.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.joey.crud.R
import com.example.joey.crud.adapter.UserAdapter
import com.example.joey.crud.data.User
import com.example.joey.crud.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, UserActivity::class.java)
            startActivityForResult(intent, userActivityRequestCode)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = UserAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        userViewModel.allUsers.observe(this, Observer { users ->
            users?.let { adapter.setUsers(it) }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == userActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.let {
                val user = User(
                    "ID: " + it.getStringExtra("ID"), "Name: " + it.getStringExtra("Name"),
                    "Email: " + it.getStringExtra("Email"), "Major: " + it.getStringExtra("Major")
                )
                userViewModel.insert(user)
            }
            Toast.makeText(this, "User created successfully.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Discarded, some fields empty.", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val userActivityRequestCode = 1
    }
}
