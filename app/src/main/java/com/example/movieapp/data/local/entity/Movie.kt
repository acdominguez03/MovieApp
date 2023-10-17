package com.example.movieapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String,
    val favorite: Boolean = false,
)
