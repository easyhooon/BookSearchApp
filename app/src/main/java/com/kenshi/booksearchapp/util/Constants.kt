package com.kenshi.booksearchapp.util

import com.kenshi.booksearchapp.BuildConfig

object Constants {
    const val BASE_URL = "https://dapi.kakao.com"
    const val API_KEY = BuildConfig.bookApiKey
    const val SEARCH_BOOKS_TIME_DELAY = 300L

    // DataStore DB Name
    const val DATASTORE_NAME = "preferences_datastore"

    // Paging
    const val PAGING_SIZE = 15
}