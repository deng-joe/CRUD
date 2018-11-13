package com.example.joey.crud.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User (
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "ID") var id: String,
    @ColumnInfo(name = "Name") var name: String,
    @ColumnInfo(name = "Email") var email: String,
    @ColumnInfo(name = "Major") var major: String
)
