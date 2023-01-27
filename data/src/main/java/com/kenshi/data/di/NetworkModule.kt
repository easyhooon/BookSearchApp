package com.kenshi.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import androidx.work.WorkManager
import com.kenshi.data.remote.api.BookSearchApi
import com.kenshi.data.local.db.BookSearchDatabase
import com.kenshi.data.utils.Constants.BASE_URL
import com.kenshi.data.utils.Constants.DATASTORE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
// AppModule -> app 전체에서 사용하는 module -> Singleton component 에 설치
object NetworkModule {

    // Retrofit
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): BookSearchApi {
        return retrofit.create(BookSearchApi::class.java)
    }

    // 객체 생성에 필요한 context 는 @ApplicationContext 를 주입 받아 사용

    // Room
    @Singleton
    @Provides
    fun provideBookSearchDatabase(@ApplicationContext context: Context): BookSearchDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            BookSearchDatabase::class.java,
            "favorite-books"
        ).build()

    // DataStore
    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(DATASTORE_NAME) }
        )

    // WorkManager
    @Singleton
    @Provides
    fun providerWorkManager(@ApplicationContext context: Context): WorkManager =
        //getInstance 로 싱글톤 객체를 만들어줌
        WorkManager.getInstance(context)


    // 캐시 최적화 결과를 반환하는 Sting 을 Worker 클래스에 주입
    @Singleton
    @Provides
    fun provideCacheDeleteResult(): String = "Cache has deleted by Hilt"
}
