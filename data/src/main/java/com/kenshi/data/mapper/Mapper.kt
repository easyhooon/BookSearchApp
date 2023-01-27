package com.kenshi.data.mapper

import com.kenshi.data.model.Book
import com.kenshi.domain.entity.BookEntity

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