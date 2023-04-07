package com.kenshi.data.source.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kenshi.data.model.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BookSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    @Query("SELECT * FROM books")
    fun getFavoriteBooks(): Flow<List<Book>>

    // pagingSource 정의
    // room 은 query 의 결과를 pagingSource 타입으로 반환해줄 수 있음
    // Retrofit 의 경우에는 (네트워크 응답) 직접 pagingSource 로 가공하는 과정이 추가되어야함
    @Query("SELECT * From books")
    fun getFavoritePagingBooks(): PagingSource<Int, Book>
}
