package com.example.joey.crud.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.joey.crud.R
import com.example.joey.crud.adapter.UserAdapter
import com.example.joey.crud.data.User
import com.example.joey.crud.viewmodel.UserViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, UserActivity::class.java)
            startActivityForResult(intent, USER_ACTIVITY_REQUEST_CODE)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = UserAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Get a new or existing ViewModel from the ViewModelProviders
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        // Add an Observer class on the LiveData
        userViewModel.allUsers.observe(this, Observer { users ->
            // Update the cached copy of the users in the adapter
            users?.let { adapter.setUsers(it) }
        })

        val simpleItemTouch = object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                userViewModel.delete(adapter.swipeToDelete(viewHolder.adapterPosition))
                Toast.makeText(this@MainActivity, "User deleted.", Toast.LENGTH_SHORT).show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouch)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == R.id.del) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Are you really sure you want to delete all data?")
            builder.setPositiveButton("OK") { _, _ ->
                userViewModel.deleteAll()
                Toast.makeText(this, "All users deleted.", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            builder.show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == USER_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
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
        const val USER_ACTIVITY_REQUEST_CODE = 1
    }
}
