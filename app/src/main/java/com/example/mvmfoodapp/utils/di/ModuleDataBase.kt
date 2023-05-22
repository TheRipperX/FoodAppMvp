package com.example.mvmfoodapp.utils.di

import android.content.Context
import androidx.room.Room
import com.example.mvmfoodapp.data.db.DataBaseFood
import com.example.mvmfoodapp.data.db.FoodEntity
import com.example.mvmfoodapp.utils.Database_Name
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleDataBase {

    @Provides
    @Singleton
    fun proDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, DataBaseFood::class.java, Database_Name
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun proDao(db: DataBaseFood) = db.foodDao()

    @Provides
    @Singleton
    fun proEntity() = FoodEntity()

}