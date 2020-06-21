package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
internal interface BreedsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBreeds(breeds: List<DbBreed>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSubbreeds(subbreeds: List<DbSubbreed>): Completable

    @Query("SELECT * FROM breed")
    fun queryBreeds(): Flowable<List<DbBreed>>

    @Query("SELECT * FROM subbreed")
    fun querySubbreeds(): Flowable<List<DbSubbreed>>
}