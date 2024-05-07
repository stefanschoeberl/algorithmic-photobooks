package dev.ssch.photobook.dsl

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory
import java.io.File
import javax.imageio.ImageIO

const val a4Width = 297.0 // mm
const val a4Height = 210.0 // mm
const val gap = 10.0 // mm

const val pointsPerInch = 72
const val pointsPerMM = 1 / (10 * 2.54) * pointsPerInch

fun Double.mm(): Float {
    return (this * pointsPerMM).toFloat()
}

fun renderImage(
    document: PDDocument,
    contentStream: PDPageContentStream,
    imagePath: String,
    x: Double,
    y: Double,
    width: Double,
    height: Double
) {
    val image = ImageIO.read(File(imagePath))
    val aspectRatioContent = height / width
    val aspectRatioImage = image.height.toDouble() / image.width

    val pdfImage = JPEGFactory.createFromImage(document, image)

    if (aspectRatioImage > aspectRatioContent) {
        val imageWidth = height / aspectRatioImage
        contentStream.drawImage(pdfImage, (x + (width - imageWidth) / 2.0).mm(), y.mm(), imageWidth.mm(), height.mm())
    } else {
        val imageHeight = width * aspectRatioImage
        contentStream.drawImage(pdfImage, x.mm(), (y + (height - imageHeight) / 2.0).mm(), width.mm(), imageHeight.mm())
    }
}


fun renderPageContents(document: PDDocument, page: Page, contentStream: PDPageContentStream) {
    val fullImageWidth = a4Width - 2 * gap
    val fullImageHeight = a4Height - 2 * gap
    val halfImageWidth = (a4Width - 3 * gap) / 2
    val halfImageHeight = (a4Height - 3 * gap) / 2

    when (page.images.size) {
        0 -> {

        }
        1 -> {
            renderImage(document, contentStream, page.images[0], gap, gap, fullImageWidth, fullImageHeight)
        }
        2 -> {
            renderImage(document, contentStream, page.images[0], gap, gap, halfImageWidth, fullImageHeight)
            renderImage(document, contentStream, page.images[1], halfImageWidth + 2 * gap, gap, halfImageWidth, fullImageHeight)
        }
        3 -> {
            renderImage(document, contentStream, page.images[0], gap, gap, halfImageWidth, halfImageHeight)
            renderImage(document, contentStream, page.images[1], gap, halfImageHeight + 2 * gap, halfImageWidth, halfImageHeight)
            renderImage(document, contentStream, page.images[2], halfImageWidth + 2 * gap, gap, halfImageWidth, fullImageHeight)
        }
        else -> {
            renderImage(document, contentStream, page.images[0], gap, gap, halfImageWidth, halfImageHeight)
            renderImage(document, contentStream, page.images[1], gap, halfImageHeight + 2 * gap, halfImageWidth, halfImageHeight)
            renderImage(document, contentStream, page.images[2], halfImageWidth + 2 * gap, gap, halfImageWidth, halfImageHeight)
            renderImage(document, contentStream, page.images[3], halfImageWidth + 2 * gap, halfImageHeight + 2 * gap, halfImageWidth, halfImageHeight)
        }
    }
}

fun renderBook(book: Book, path: String) {
    PDDocument().use { document ->

        book.pages.forEach { page ->
            val pdfPage = PDPage(PDRectangle(a4Width.mm(), a4Height.mm()))
            document.addPage(pdfPage)
            PDPageContentStream(document, pdfPage).use {
                renderPageContents(document, page, it)
            }
        }

        document.save(path)
    }

}
