package dev.ssch.photobook.dsl

@DslMarker
annotation class PhotoBookDsl

@PhotoBookDsl
class PageBuilder (
    val images: MutableList<String> = mutableListOf()
) {
    operator fun String.unaryPlus() {
        images.add(this)
    }
}

@PhotoBookDsl
class BookBuilder (
    val pages: MutableList<Page> = mutableListOf()
) {
    fun page(init: PageBuilder.() -> Unit) {
        val pageBuilder = PageBuilder()
        pageBuilder.init()
        pages.add(Page(pageBuilder.images))
    }
}

fun book(init: BookBuilder.() -> Unit): Book {
    val bookBuilder = BookBuilder()
    bookBuilder.init()
    return Book(bookBuilder.pages)
}
