package com.lucifer.chapterly.book.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 *  Factory class to create Room database builder for FavoriteBookDatabase for Android platform.
 */
actual class DatabaseFactory(
    private val context: Context
) {
    actual fun create(): RoomDatabase.Builder<FavoriteBookDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(FavoriteBookDatabase.DB_NAME)

        return Room.databaseBuilder(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}