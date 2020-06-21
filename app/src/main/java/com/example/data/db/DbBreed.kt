package com.example.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "breed")
internal data class DbBreed(
        @PrimaryKey
        @ColumnInfo(name = "name")
        val name: String
)
