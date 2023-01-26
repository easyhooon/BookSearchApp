package com.kenshi.booksearchapp.presentation.mapper

import com.kenshi.booksearchapp.domain.entity.BookEntity
import com.kenshi.booksearchapp.presentation.item.BookItem

internal fun BookItem.toDomain() = BookEntity(
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

internal fun BookEntity.toItem() = BookItem(
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