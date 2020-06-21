package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
        entities = [
            DbBreed::class,
            DbSubbreed::class
        ],
        version = 1
)
internal abstract class PupzDatabase : RoomDatabase() {

    abstract fun breedsDao(): BreedsDao

    companion object {
        private const val DATABASE_NAME = "pupz-db"

        @Volatile
        private var database: PupzDatabase? = null

        fun getInstance(context: Context): PupzDatabase {
            return database ?: synchronized(this) {
                database ?: Room.databaseBuilder(context, PupzDatabase::class.java, DATABASE_NAME)
                        .build().also { database = it }
            }
        }
    }
}
