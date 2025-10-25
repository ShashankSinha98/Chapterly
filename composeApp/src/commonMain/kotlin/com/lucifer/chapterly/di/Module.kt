package com.lucifer.chapterly.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.lucifer.chapterly.book.data.database.DatabaseFactory
import com.lucifer.chapterly.book.data.database.FavoriteBookDatabase
import com.lucifer.chapterly.book.data.network.KtorRemoteBookDataSource
import com.lucifer.chapterly.book.data.network.RemoteBookDataSource
import com.lucifer.chapterly.book.data.repository.DefaultBookRepository
import com.lucifer.chapterly.book.domain.BookRepository
import com.lucifer.chapterly.book.presentation.SelectedBookViewModel
import com.lucifer.chapterly.book.presentation.book_detail.BookDetailViewModel
import com.lucifer.chapterly.book.presentation.book_list.BookListViewModel
import com.lucifer.chapterly.core.data.HttpClientFactory
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module // Platform-specific dependencies

val sharedModule = module {
    // Provide a singleton HttpClient instance
    single {
        HttpClientFactory.create(
            engine = get() // will be platform-specific (android, desktop = OkHttp, iOS = Darwin)
        )
    }

    /** Provide RemoteBookDataSource implementation
        singleOf creates a singleton of the given type and binds it to the specified interface
        All dependencies will be automatically injected by Koin (get() calls)
        In normal singleOf, we need to pass multiple get() calls for each dependency, no need in singleOf */
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()

    singleOf(::DefaultBookRepository).bind<BookRepository>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build() // Returns FavoriteBookDatabase
    }

    single { get<FavoriteBookDatabase>().favoriteBookDao }

    viewModelOf(::BookListViewModel)
    viewModelOf(::SelectedBookViewModel)
    viewModelOf(::BookDetailViewModel)
}