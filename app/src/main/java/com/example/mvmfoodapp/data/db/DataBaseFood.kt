package com.example.mvmfoodapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvmfoodapp.utils.DatabaseVersion

@Database(entities = [FoodEntity::class], version = DatabaseVersion, exportSchema = false)
abstract class DataBaseFood: RoomDatabase() {
    abstract fun foodDao(): FoodDao
}