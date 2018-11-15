package com.example.joey.crud.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import com.example.joey.crud.R
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {
    private lateinit var idView: EditText
    private lateinit var nameView: EditText
    private lateinit var emailView: EditText
    private lateinit var majorView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = "Add User"

        idView = findViewById(R.id.id)
        nameView = findViewById(R.id.name)
        emailView = findViewById(R.id.email)
        majorView = findViewById(R.id.major)

        val button = findViewById<Button>(R.id.create)
        button.setOnClickListener {
            val intent = Intent()
            if (TextUtils.isEmpty(id.text) && TextUtils.isEmpty(name.text) && TextUtils.isEmpty(email.text) && TextUtils.isEmpty(major.text)) {
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                val userId = idView.text.toString()
                val userName = nameView.text.toString()
                val userEmail = emailView.text.toString()
                val userMajor = majorView.text.toString()
                intent.putExtra("ID", userId)
                intent.putExtra("Name", userName)
                intent.putExtra("Email", userEmail)
                intent.putExtra("Major", userMajor)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
    }
}
