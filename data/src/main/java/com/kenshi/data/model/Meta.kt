package com.kenshi.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// Json 만으로는 kotlin 에서 변환 실패함 field 추가
@Keep
@JsonClass(generateAdapter = true)
data class Meta(
    @field:Json(name = "total_count")
    val totalCount: Int, // total_count: 검색된 문서 수
    @field:Json(name = "pageable_count")
    val pageableCount: Int, // pageable_count: total_count 중 노출 가능 문서수
    @field:Json(name = "is_end")
    val isEnd: Boolean, // is_end: 현재 페이지가 마지막 페이지인지 여부, false 면 page 를 증가시켜 다음 페이지 요청 가능
)
