package com.udacity.asteroidradar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import org.json.JSONObject

class TheRepository ( private val dao: AsteroidDao){

    val type = MutableLiveData<Int>()

    @RequiresApi(Build.VERSION_CODES.N)
    fun getTheAsteroids(): LiveData<List<Asteroid>?> {
            var aster = dao.getAsteroids()
        when (type.value) {
            1 -> {aster= dao.getAsteroids()}
            2 -> {aster= dao.getAsteroidsToday()}
            3 -> {aster= dao.getAsteroidsWeek()}
            else -> {aster= dao.getAsteroids()}
        }
        return aster
    }

    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun refreshTheAsteroids() {

        try {
            val c = TheApi.retrofitService.getTheAsteroids().body()
            val d = parseAsteroidsJsonResult(JSONObject(c))
            if (d != null){

//                we used toTypedArray() to use it with vararg
                 dao.insertAll(*d.toTypedArray())

            }

        } catch( e:Exception){
            Log.i("MainViewModel", e.message.toString())
        }

    }

    fun deleteAll() = dao.deleteAllAsteroids()

}