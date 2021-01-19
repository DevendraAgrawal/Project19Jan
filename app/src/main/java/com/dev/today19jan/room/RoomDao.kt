package com.dev.today19jan.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RoomDao {

    @Query("SELECT * FROM User")
    fun getAllUserData() : List<RoomEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUserData(data: RoomEntity)

    @Query("UPDATE User SET completed = :completed WHERE id=:id")
    fun updateUserData(completed: Boolean, id: Int)

}