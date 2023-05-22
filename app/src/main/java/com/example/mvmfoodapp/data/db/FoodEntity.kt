package com.example.mvmfoodapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mvmfoodapp.utils.Food_Table

@Entity(tableName = Food_Table)
data class FoodEntity(
    @PrimaryKey
    var id: Int = 0,
    var foodName: String = "",
    var foodImage: String = ""
)