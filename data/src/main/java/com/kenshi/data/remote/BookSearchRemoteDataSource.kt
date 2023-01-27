package com.kenshi.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.kenshi.data.remote.api.BookSearchApi
import com.kenshi.data.mapper.toEntity
import com.kenshi.data.paging.BookSearchPagingSource
import com.kenshi.data.utils.Constants
import com.kenshi.domain.entity.BookEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookSearchRemoteDataSource @Inject constructor(
    private val bookSearchApi: BookSearchApi
) {
    fun searchBooks(query: String, sort: String): Flow<PagingData<BookEntity>> {
        val pagingSourceFactory = { BookSearchPagingSource(bookSearchApi, query, sort) }

        return Pager(
            // pager 를 구현하기 위해서는
            // pagingConfig 를 통해 parameter 를 전달 해줘야함
            config = PagingConfig(
                // 어떤 기기로 동작 시키든 뷰홀더에 표시할 데이터가 모자르지 않을 정도의 값으로 설정
                pageSize = Constants.PAGING_SIZE,
                // true -> repository 의 전체 데이터 사이즈를 받아와서 recyclerview 의 placeholder 를 미리 만들어 놓음
                // 화면에 표시 되지 않는 항목은 null로 표시
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
                book.toEntity()
            }
        }
    }
}