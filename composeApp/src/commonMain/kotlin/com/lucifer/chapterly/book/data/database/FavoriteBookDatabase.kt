package com.lucifer.chapterly.book.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [BookEntity::class],
    version = 1
)
@TypeConverters(
    StringListTypeConverter::class
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class FavoriteBookDatabase: RoomDatabase() {

    abstract val favoriteBookDao: FavoriteBookDao

    companion object {
        val DB_NAME = "favorite_books_db"
    }
}