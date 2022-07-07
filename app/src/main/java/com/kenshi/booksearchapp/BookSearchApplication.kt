package com.kenshi.booksearchapp

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

// 모든 힐트 컴포넌트의 상위 스코프 == Application class
@HiltAndroidApp
class BookSearchApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    // worker 클래스가 HiltWorkerFactory 를 통해 생성되게 됨
    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}