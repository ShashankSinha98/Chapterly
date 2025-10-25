package com.lucifer.chapterly.book.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

/**
 *  Factory class to create Room database builder for FavoriteBookDatabase for JVM platforms.
 */
actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<FavoriteBookDatabase> {
        val os = System.getProperty("os.name").lowercase()
        val userHome = System.getProperty("user.home")
        val appDataDir = when {
            os.contains("win") -> File(System.getenv("APPDATA"),"Chapterly")
            os.contains("mac") -> File(userHome, "Library/Application Support/Chapterly")
            else -> File(userHome, ".local/share/Chapterly")
        }

        if (!appDataDir.exists()) {
            appDataDir.mkdirs()
        }
        val dbFile = File(appDataDir, FavoriteBookDatabase.DB_NAME)
        return Room.databaseBuilder(dbFile.absolutePath)
    }
}