package com.kenshi.booksearchapp.di

import android.content.Context
import androidx.room.Room
import com.kenshi.data.source.local.db.BookSearchDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    // test 시에는 BookSearchDatabase 가 테스트할때 생성되고 사라지는 일회성 객체 싱글톤을 붙히진 않음
    @Provides
    // hilt 가 객체를 구분할 수 있도록 (BookSearchDatabase 주입 코드가 다른 곳에도 존재하기 때문에) 명시
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context): BookSearchDatabase =
        Room.inMemoryDatabaseBuilder(context, BookSearchDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}
