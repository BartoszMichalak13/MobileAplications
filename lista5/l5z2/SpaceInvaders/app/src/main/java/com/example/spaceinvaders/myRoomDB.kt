package com.example.spaceinvaders

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HighestScore::class], version = 1)
abstract class myRoomDB : RoomDatabase() {
    abstract fun myDao(): MyDao
}