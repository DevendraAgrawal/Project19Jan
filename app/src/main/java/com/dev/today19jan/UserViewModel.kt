package com.dev.today19jan

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.today19jan.room.RoomDao
import com.dev.today19jan.room.UserRoomDatabase
import com.dev.today19jan.room.RoomEntity
import com.google.gson.Gson
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

class UserViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    val sJob = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + sJob

    var _loading = MutableLiveData<Boolean>()

    var _data: MutableLiveData<ArrayList<RoomEntity>>? = null

    private var roomDao: RoomDao

    init {

        val db = UserRoomDatabase.getDatabase(application)
        roomDao = db.roomDao()

    }

    fun getAllData(): LiveData<ArrayList<RoomEntity>> {

        if (_data == null) {
            _data = MutableLiveData()
            loadAllData()
        }
        return _data as MutableLiveData<ArrayList<RoomEntity>>
    }

    private fun loadAllData() {

        launch {
            _loading.postValue(true)
            val retrofit = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create()).build()

            val services = retrofit.create(Services::class.java)

            services.getData().enqueue(object : Callback<ArrayList<RoomEntity>> {
                override fun onResponse(
                    call: Call<ArrayList<RoomEntity>>,
                    response: Response<ArrayList<RoomEntity>>
                ) {
                    Log.e("Dev", Gson().toJson(response.body()))
                    _data?.postValue(response.body())
                    _loading.postValue(false)
                }

                override fun onFailure(call: Call<ArrayList<RoomEntity>>, t: Throwable) {
                    Log.e("Dev", call.toString())
                    _loading.postValue(false)
                }

            })
        }
    }

    fun insertUserData(data: RoomEntity) {

        launch {
            roomDao.insertUserData(data)
        }
    }

    fun updateUserData(completed: Boolean, id: Int){
        launch {
            roomDao.updateUserData(completed, id)
        }
    }

    fun getAllUserData(): List<RoomEntity>{
        val data: List<RoomEntity> = roomDao.getAllUserData()
        return data
    }
}