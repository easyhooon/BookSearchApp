package com.kenshi.booksearchapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kenshi.booksearchapp.data.local.BookSearchDao
import com.kenshi.booksearchapp.data.model.Book

@Database(
    entities = [Book::class],
    version = 1,
    exportSchema = false
)

//typeConverter 를 지정 하면 room 에서 알아서 type convert 작업을 처리해준다.
@TypeConverters(OrmConverter::class)
abstract class BookSearchDatabase : RoomDatabase() {

    //dao 지정
    abstract fun bookSearchDao(): BookSearchDao

    // Hilt 도입 이후 필 요없어짐
    // singleton setting
//    companion object {
//        @Volatile
//        private var INSTANCE: BookSearchDatabase? = null
//
//        private fun buildDatabase(context: Context): BookSearchDatabase =
//            Room.databaseBuilder(
//                context.applicationContext,
//                BookSearchDatabase::class.java,
//                "favorite-books"
//            ).build()
//
//        fun getInstance(context: Context): BookSearchDatabase =
//            INSTANCE ?: synchronized(this) {
//                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
//            }
//
//    }
}