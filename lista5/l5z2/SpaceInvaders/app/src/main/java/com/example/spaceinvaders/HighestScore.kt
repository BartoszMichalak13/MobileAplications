package com.example.spaceinvaders

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "highestscore")
data class HighestScore(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "score") val score: Int?
)
