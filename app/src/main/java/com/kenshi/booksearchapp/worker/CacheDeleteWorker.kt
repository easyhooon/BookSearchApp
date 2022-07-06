package com.kenshi.booksearchapp.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class CacheDeleteWorker(
    context: Context,
    workerParams: WorkerParameters,
) : Worker(context, workerParams) {

    //doWork 내부에 백그라운드 작업을 명시
    override fun doWork(): Result {
        return try {
            //백그라운드 작업부
            Log.d("WorkManager", "Cache has successfully deleted")
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}