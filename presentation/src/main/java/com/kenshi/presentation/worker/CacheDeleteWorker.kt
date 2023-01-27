package com.kenshi.presentation.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

// worker 가 hilt 의존성 주입을 받을 수 있도록 annotation 을 붙혀줌
// 제한사항: worker 는 singletonComponent 안에 설치된 의존성 만을 주입 받을 수 있음
// AppModule 은 SingletonComponent 안에 설치되어 있기 때문에 주입 받을 수 있음
// 이렇게 정의된 Worker 클래스는 HiltWorkerFactory 를 통해 생성 해야함, Application 단에서 설정
@HiltWorker
class CacheDeleteWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val cacheDeleteResult: String,
) : Worker(context, workerParams) {

    //doWork 내부에 백그라운드 작업을 명시
    override fun doWork(): Result {
        return try {
            // 백그라운드 작업부
            // Log.d("WorkManager", "Cache has successfully deleted")
            Log.d("WorkManager", cacheDeleteResult)
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}