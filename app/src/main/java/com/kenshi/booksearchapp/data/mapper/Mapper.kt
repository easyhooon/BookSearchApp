package com.kenshi.booksearchapp.data.mapper

import com.kenshi.booksearchapp.data.model.Book
import com.kenshi.booksearchapp.domain.entity.BookEntity

internal fun Book.toEntity() = BookEntity(
    title = title,
    contents = contents,
    url = url,
    isbn = isbn,
    datetime = datetime,
    authors = authors,
    publisher = publisher,
    translators = translators,
    price = price,
    salePrice = salePrice,
    thumbnail = thumbnail,
    status = status
)

internal fun BookEntity.toModel() = Book(
    title = title,
    contents = contents,
    url = url,
    isbn = isbn,
    datetime = datetime,
    authors = authors,
    publisher = publisher,
    translators = translators,
    price = price,
    salePrice = salePrice,
    thumbnail = thumbnail,
    status = status
)