package com.example.mvmfoodapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mvmfoodapp.utils.Food_Table
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

@Dao
interface FoodDao {

    @Insert
    fun foodInsert(foodEntity: FoodEntity): Completable

    @Delete
    fun foodDelete(foodEntity: FoodEntity): Completable

    @Query("SELECT * FROM $Food_Table")
    fun foodAllShow(): Observable<MutableList<FoodEntity>>

    @Query("SELECT EXISTS(SELECT * FROM $Food_Table WHERE id = :id)")
    fun findIdFood(id: Int) : Observable<Boolean>
}