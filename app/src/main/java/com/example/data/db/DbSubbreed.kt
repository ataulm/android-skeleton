package com.example.data.db

import androidx.room.*

@Entity(
        tableName = "subbreed",
        indices = [Index("breedName")],
        foreignKeys = [
            ForeignKey(
                    entity = DbBreed::class,
                    parentColumns = ["name"],
                    childColumns = ["breedName"],
                    onDelete = ForeignKey.CASCADE
            )
        ]
)
internal data class DbSubbreed(
        /**
         * Can't guarantee that two subbreeds won't have the same name
         * so this is a combo of the breedName and subbreed.
         */
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: String,

        @ColumnInfo(name = "name")
        val name: String,

        @ColumnInfo(name = "breedName")
        val breedName: String
)
