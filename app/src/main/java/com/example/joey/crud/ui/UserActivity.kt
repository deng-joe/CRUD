package com.example.joey.crud.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.joey.crud.R

class UserActivity : AppCompatActivity() {
    private lateinit var idView: EditText
    private lateinit var nameView: EditText
    private lateinit var emailView: EditText
    private lateinit var majorView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        idView = findViewById(R.id.id)
        nameView = findViewById(R.id.name)
        emailView = findViewById(R.id.email)
        majorView = findViewById(R.id.major)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        setTitle()

        val save = findViewById<Button>(R.id.create)
        save.setOnClickListener {
            saveUser()
        }
    }

    private fun setTitle() {
        val intent = intent
        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit User"
            idView.setText(intent.getStringExtra(EXTRA_ID))
            nameView.setText(intent.getStringExtra(EXTRA_NAME))
            emailView.setText(intent.getStringExtra(EXTRA_EMAIL))
            majorView.setText(intent.getStringExtra(EXTRA_MAJOR))
        } else {
            title = "Add User"
        }
    }

    private fun saveUser() {
        val userId = idView.text.toString()
        val userName = nameView.text.toString()
        val userEmail = emailView.text.toString()
        val userMajor = majorView.text.toString()

        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(userName) || TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userMajor)) {
            Toast.makeText(this, "Please leave no field empty.", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent()
        intent.putExtra(EXTRA_ID, userId)
        intent.putExtra(EXTRA_NAME, userName)
        intent.putExtra(EXTRA_EMAIL, userEmail)
        intent.putExtra(EXTRA_MAJOR, userMajor)

        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    companion object {
        const val EXTRA_ID = "com.example.joey.crud.ui.EXTRA_ID"
        const val EXTRA_NAME = "com.example.joey.crud.ui.EXTRA_NAME"
        const val EXTRA_EMAIL = "com.example.joey.crud.ui.EXTRA_EMAIL"
        const val EXTRA_MAJOR = "com.example.joey.crud.ui.EXTRA_MAJOR"
    }
}
