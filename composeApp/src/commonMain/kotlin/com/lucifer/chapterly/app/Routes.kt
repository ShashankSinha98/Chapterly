package com.lucifer.chapterly.app

import kotlinx.serialization.Serializable

sealed interface Routes {

    @Serializable
    data object BookGraph : Routes

    // Add other graphs for new features here in the future (e.g., AuthGraph)
}

sealed interface BookGraphRoutes {
    @Serializable
    data object BookList : BookGraphRoutes

    @Serializable
    data class BookDetail(val bookId: String) : BookGraphRoutes
}