package com.kenshi.booksearchapp.data.db

import androidx.room.*
import com.kenshi.booksearchapp.data.model.Book
import kotlinx.coroutines.flow.Flow


@Dao
interface BookSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    // query 를 제외한 cud 작업은 시간이 오래걸리는 작업이라 suspend 키워드
//    @Query("SELECT * FROM books")
//    fun getFavoriteBooks(): LiveData<List<Book>>

    @Query("SELECT * FROM books")
    fun getFavoriteBooks(): Flow<List<Book>>
}