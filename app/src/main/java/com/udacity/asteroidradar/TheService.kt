package com.udacity.asteroidradar

import android.os.Build
import androidx.annotation.RequiresApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidService {

    //"/neo/rest/v1/feed?start_date=2015-09-07&end_date=2015-09-08&api_key=V6yLdxQr8W6dB6nFRdthDsEPIkA5JnBOgFflW4a4"
    @RequiresApi(Build.VERSION_CODES.N)
    @GET(Constants.THE_ASTEROID_URL)
    suspend fun getTheAsteroids(@Query("start_date") start:String = getNextSevenDaysFormattedDates()[0], @Query("end_date")
    end:String = getNextSevenDaysFormattedDates()[7], @Query(Constants.THE_KEY_URL) key:String = Constants.THE_KEY
    ): Response<String>

    @GET(Constants.THE_PIC_OF_THE_DAY_URL)
    suspend fun getThePic(@Query(Constants.THE_KEY_URL) key:String = Constants.THE_KEY): Response<PictureOfDay>

}