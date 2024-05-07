package dev.ssch.photobook.dsl

data class Book (
    val pages: List<Page>
)

data class Page (
    val images: List<String>
)
