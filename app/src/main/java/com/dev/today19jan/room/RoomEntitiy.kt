package com.dev.today19jan.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class RoomEntity(var userId: Int,
                       @PrimaryKey var id: Int,
                       var title: String,
                       var completed: Boolean)