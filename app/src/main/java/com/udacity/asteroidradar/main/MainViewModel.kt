package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.udacity.asteroidradar.*
import com.udacity.asteroidradar.AsteroidDatabase.Companion.getDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)

    private val dao = database.dao

    private val theRepository = TheRepository(getDatabase(application).dao)

    val type = theRepository.type

    private val _p = MutableLiveData<PictureOfDay?>()
     val  p : LiveData<PictureOfDay?>
        get() = _p

    val t: LiveData<Int> = type


    val w: LiveData<List<Asteroid>> = Transformations.switchMap(t) {

        theRepository.getTheAsteroids()

    }


    init {

        type.value = 1

        viewModelScope.launch(Dispatchers.IO) {

            theRepository.refreshTheAsteroids()

            getPic()

        }

    }

    private suspend fun getPic() {
        try {
            val c = TheApi.retrofitService.getThePic().body()
            if (c != null){
                _p.postValue(c)
            }
        } catch( e:Exception){
            Log.i("MainViewModel", e.message.toString())
        }

    }

}
