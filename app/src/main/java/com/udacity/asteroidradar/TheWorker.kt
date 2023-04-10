package com.udacity.asteroidradar

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.AsteroidDatabase.Companion.getDatabase
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters, private val repository: TheRepository = TheRepository(getDatabase(appContext).dao)):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun doWork(): Result {

        return try {
            repository.deleteAll()
            repository.type.value = 3
            repository.refreshTheAsteroids()
            Result.success()
        } catch (exception: HttpException) {
            Result.retry()
        }
    }
}