package com.kenshi.booksearchapp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kenshi.booksearchapp.data.api.RetrofitInstance.api
import com.kenshi.booksearchapp.data.db.BookSearchDatabase
import com.kenshi.booksearchapp.data.model.Book
import com.kenshi.booksearchapp.data.model.SearchResponse
import com.kenshi.booksearchapp.data.repository.BookSearchRepositoryImpl.PreferenceKeys.SORT_MODE
import com.kenshi.booksearchapp.util.Sort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import retrofit2.Response
import java.io.IOException

class BookSearchRepositoryImpl(
    private val db: BookSearchDatabase,
    private val dataStore: DataStore<Preferences>,
) : BookSearchRepository {
    override suspend fun searchBooks(
        query: String,
        sort: String,
        page: Int,
        size: Int,
    ): Response<SearchResponse> {
        return api.searchBooks(query, sort, page, size)
    }

    override suspend fun insertBooks(book: Book) {
        db.bookSearchDao().insertBook(book)
    }

    override suspend fun deleteBooks(book: Book) {
        db.bookSearchDao().deleteBook(book)
    }

//    override fun getFavoriteBooks(): LiveData<List<Book>> {
//        return db.bookSearchDao().getFavoriteBooks()
//    }

    override fun getFavoriteBooks(): Flow<List<Book>> {
        return db.bookSearchDao().getFavoriteBooks()
    }

    // DataStore
    private object PreferenceKeys {
        // key 로 string 을 사용하던 spf 와 다르게 type-safe 를 위해 preferencesKey 를 사용
        // 저장할 type 이 string 이기때문에 stringPreferencesKey
        val SORT_MODE = stringPreferencesKey("sort_mode")
    }

    // 저장 작업은 coroutine 블럭에서 이루어짐
    override suspend fun saveSortMode(mode: String) {
        dataStore.edit { prefs ->
            prefs[SORT_MODE] = mode
        }
    }

    override suspend fun getSortMode(): Flow<String> {
        // 파일에 접근하기 위해 data 메소드를 사용
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            // map 블럭 내에서 key 를 전달해서 flow 를 반환을 받음
            // 기본 값으로는 ACCURACY 값이 나오도록
            .map { prefs ->
                prefs[SORT_MODE] ?: Sort.ACCURACY.value
            }
    }
}