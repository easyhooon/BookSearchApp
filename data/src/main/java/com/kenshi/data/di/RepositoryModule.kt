package com.kenshi.data.di

import com.kenshi.booksearchapp.domain.BookSearchRepository
import com.kenshi.data.repository.BookSearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//BookSearchRepository 는 interface 이기 때문에 @Binds 를 사용 해서 Hilt 가 의존성 객체를 생성할 수 있게 설정 해줌

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindBookSearchRepository(
        bookSearchRepositoryImpl: BookSearchRepositoryImpl,
    ): BookSearchRepository
}