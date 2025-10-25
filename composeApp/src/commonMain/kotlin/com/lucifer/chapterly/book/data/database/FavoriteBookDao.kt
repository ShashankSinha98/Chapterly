package com.lucifer.chapterly.book.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBookDao {

    @Upsert // Inserts a book or updates it if it already exists
    suspend fun upsertBook(bookEntity: BookEntity)

    @Query("SELECT * FROM BookEntity")
    fun getFavoriteBooks(): Flow<List<BookEntity>> // Returns a flow of favorite books (not suspend bcz it's observable)

    @Query("SELECT * FROM BookEntity WHERE id = :id")
    suspend fun getFavoriteBook(id: String): BookEntity? // Get a favorite book by its ID

    @Query("DELETE FROM BookEntity WHERE id = :id")
    suspend fun deleteFavoriteBook(id: String) // Deletes a favorite book by its ID
}