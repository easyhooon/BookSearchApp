package com.kenshi.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kenshi.data.source.local.BookSearchDao
import com.kenshi.data.model.Book

@Database(
    entities = [Book::class],
    version = 1,
    exportSchema = false
)

// typeConverter 를 지정 하면 room 에서 알아서 type convert 작업을 처리 해줌
@TypeConverters(OrmConverter::class)
abstract class BookSearchDatabase : RoomDatabase() {

    // dao 지정
    abstract fun bookSearchDao(): BookSearchDao
}
