package com.dev.today19jan

import com.dev.today19jan.room.RoomEntity
import retrofit2.Call
import retrofit2.http.GET

interface Services {

    @GET("todos")
    fun getData(): Call<ArrayList<RoomEntity>>
}