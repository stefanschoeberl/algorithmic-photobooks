package dev.ssch.photobook.dsl

fun main() {
    val book = book {
        page {
            + "images/bilbao.jpg"
        }
        page {
            + "images/berlin.jpg"
            + "images/budapest.jpg"
        }
        page {
            + "images/berlin.jpg"
            + "images/bilbao.jpg"
            + "images/budapest.jpg"
        }
    }
    renderBook(book, "book.pdf")
}
