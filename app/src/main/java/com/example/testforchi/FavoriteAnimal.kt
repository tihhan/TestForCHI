package com.example.testforchi

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteAnimal(
    @PrimaryKey
    val imageUrl: String
)

