package com.dev.today19jan.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [RoomEntity::class], version = 2)
abstract class UserRoomDatabase: RoomDatabase() {

    abstract fun roomDao() : RoomDao

    companion object {
        private var databaseInstance: UserRoomDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): UserRoomDatabase {
            if(databaseInstance == null){
                databaseInstance = Room.databaseBuilder(context.applicationContext, UserRoomDatabase::class.java, "User")
                    .addCallback(callBack).allowMainThreadQueries()
                    .build()
            }

            return databaseInstance as UserRoomDatabase
        }

        private val callBack = object : Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }
    }
}