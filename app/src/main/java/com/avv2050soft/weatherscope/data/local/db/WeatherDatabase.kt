package com.avv2050soft.weatherscope.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.avv2050soft.weatherscope.data.local.entities.LocationInDbItem

@Database(
    entities = [LocationInDbItem::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun locationsDao(): LocationsDao

    companion object {
        private const val databaseName = "weather_scope"

        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    databaseName
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}