package com.kenshi.data.di

import android.content.Context
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WorkManagerModule {

    // WorkManager
    @Singleton
    @Provides
    fun providerWorkManager(@ApplicationContext context: Context): WorkManager =
        // getInstance 로 싱글톤 객체를 만들어줌
        WorkManager.getInstance(context)

    // 캐시 최적화 결과를 반환하는 Sting 을 Worker 클래스에 주입
    @Singleton
    @Provides
    fun provideCacheDeleteResult(): String = "Cache has deleted by Hilt"
}
