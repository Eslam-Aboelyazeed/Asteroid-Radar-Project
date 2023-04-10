package com.udacity.asteroidradar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates

@Dao
interface AsteroidDao{

    @RequiresApi(Build.VERSION_CODES.N)
    @Query("SELECT * FROM the_asteroid WHERE the_date >= :date ORDER BY the_date ASC")
    fun getAsteroids(date:String = getNextSevenDaysFormattedDates()[0]): LiveData<List<Asteroid>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg asteroid: Asteroid)

    @RequiresApi(Build.VERSION_CODES.N)
    @Query("SELECT * FROM the_asteroid WHERE the_date = :date")
    fun getAsteroidsToday(date:String = getNextSevenDaysFormattedDates()[0]): LiveData<List<Asteroid>?>

    @RequiresApi(Build.VERSION_CODES.N)
    @Query("SELECT * FROM the_asteroid WHERE the_date > :sdate and the_date <= :edate ORDER BY the_date ASC")
    fun getAsteroidsWeek(sdate:String = getNextSevenDaysFormattedDates()[0],edate:String = getNextSevenDaysFormattedDates()[7]): LiveData<List<Asteroid>?>

    @Query("DELETE FROM the_asteroid")
    fun deleteAllAsteroids()
}
