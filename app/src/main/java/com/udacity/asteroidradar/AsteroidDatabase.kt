package com.udacity.asteroidradar

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Asteroid::class], version = 3, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val dao: AsteroidDao

    companion object {
        @Volatile
        private var INSTANCE: AsteroidDatabase? = null

        fun getDatabase(context: Context): AsteroidDatabase {

            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java,
                        "asteroids"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }

        }
    }
}