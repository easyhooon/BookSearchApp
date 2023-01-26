package com.kenshi.booksearchapp.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.kenshi.booksearchapp.data.db.BookSearchDatabase
import com.kenshi.booksearchapp.data.local.BookSearchLocalDataSource.PreferenceKeys.CACHE_DELETE_MODE
import com.kenshi.booksearchapp.data.local.BookSearchLocalDataSource.PreferenceKeys.SORT_MODE
import com.kenshi.booksearchapp.data.mapper.toDomain
import com.kenshi.booksearchapp.data.mapper.toModel
import com.kenshi.booksearchapp.domain.entity.BookEntity
import com.kenshi.booksearchapp.util.Constants
import com.kenshi.booksearchapp.util.Sort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class BookSearchLocalDataSource @Inject constructor(
    private val bookSearchDatabase: BookSearchDatabase,
    private val dataStore: DataStore<Preferences>
) {

    suspend fun insertBook(bookEntity: BookEntity) {
        bookSearchDatabase.bookSearchDao().insertBook(bookEntity.toModel())
    }

    suspend fun deleteBook(bookEntity: BookEntity) {
        bookSearchDatabase.bookSearchDao().deleteBook(bookEntity.toModel())
    }

    fun getFavoriteBooksForTest(): Flow<List<BookEntity>> {
        return bookSearchDatabase.bookSearchDao().getFavoriteBooks().map { list ->
            list.map { book ->
                book.toDomain()
            }
        }
    }

    private object PreferenceKeys {
        // key 로 string 을 사용하던 spf 와 다르게 type-safe 를 위해 preferencesKey 를 사용
        // 저장할 type 이 string 이기때문에 stringPreferencesKey
        val SORT_MODE = stringPreferencesKey("sort_mode")
        val CACHE_DELETE_MODE = booleanPreferencesKey("cache_delete_mode")
    }

    // 저장 작업은 coroutine block 내에서 이루어짐
    suspend fun saveSortMode(mode: String) {
        dataStore.edit { prefs ->
            prefs[SORT_MODE] = mode
        }
    }

    suspend fun getSortMode(): Flow<String> {
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

    suspend fun saveCacheDeleteMode(mode: Boolean) {
        dataStore.edit { prefs ->
            prefs[CACHE_DELETE_MODE] = mode
        }
    }

    suspend fun getCacheDeleteMode(): Flow<Boolean> {
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
                prefs[CACHE_DELETE_MODE] ?: false
            }
    }

    fun getFavoriteBooks(): Flow<PagingData<BookEntity>> {
        val pagingSourceFactory = {
            bookSearchDatabase.bookSearchDao().getFavoritePagingBooks()
        }

        return Pager(
            // pager 를 구현하려면
            // pagingConfig 를 통해 parameter 를 전달 해줘야함
            config = PagingConfig(
                // 어떤 기기로 동작 시키든 뷰홀더에 표시할 데이터가 모자르지 않을 정도의 값으로 설정
                pageSize = Constants.PAGING_SIZE,
                // true -> repository 의 전체 데이터 사이즈를 받아와서 recyclerview 의 placeholder 를 미리 만들어놓음
                // 화면에 표시되지 않는 항목은 null로 표시
                // 필요할 때 필요한 만큼만 로딩 하려면 false
                enablePlaceholders = false,
                // 페이저가 메모리에 가지고 있을 수 있는 최대 개수, 페이지 사이즈의 2~3배 정도
                maxSize = Constants.PAGING_SIZE * 3
            ),
            // api 호출 결과를 팩토리에 전달
            pagingSourceFactory = pagingSourceFactory
            // 결과를 flow 로 변환
        ).flow.map { pagingData ->
            pagingData.map { book ->
                book.toDomain()
            }
        }
    }
}