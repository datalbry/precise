package io.datalbry.precise.api.schema.document.generic

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GenericDocumentTest {

    @Test
    fun equals_documentsWithSimpleField_isTrue() {
        val doc1 = GenericDocument("test", "12489124", setOf(GenericField("name", "test")))
        val doc2 = GenericDocument("test", "12489124", setOf(GenericField("name", "test")))

        assertEquals(doc1, doc2)
    }

    @Test
    fun equals_documentsWithSimpleFields_isTrue() {
        val doc1 = GenericDocument("book", "test", setOf(
                GenericField("name", "test"),
                GenericField("title", "Peter Pan"),
                GenericField("author", "J. M. Barrie")
        ))
        val doc2 = GenericDocument("book", "test", setOf(
                GenericField("name", "test"),
                GenericField("title", "Peter Pan"),
                GenericField("author", "J. M. Barrie")
        ))

        assertEquals(doc1, doc2)
    }

    @Test
    fun equals_documentsWithSimpleArrayFields_isTrue() {
        val doc1 = GenericDocument("book","test", setOf(
            GenericField("name", setOf("test", "anotherTest")),
            GenericField("title", "Peter Pan"),
            GenericField("author", "J. M. Barrie")
        ))
        val doc2 = GenericDocument("book","test", setOf(
            GenericField("name", setOf("test", "anotherTest")),
            GenericField("title", "Peter Pan"),
            GenericField("author", "J. M. Barrie")
        ))

        assertEquals(doc1, doc2)
    }

    @Test
    fun equals_documentsWithRecord_isTrue() {
        val authorRecord1 = GenericRecord("author", setOf(
                GenericField("name", "J. M. Barrie"),
                GenericField("date_of_birth", "May 9, 1860")
        ))
        val authorRecord2 = GenericRecord("author", setOf(
            GenericField("name", "J. M. Barrie"),
            GenericField("date_of_birth", "May 9, 1860")
        ))
        val doc1 = GenericDocument("book","test",setOf(
            GenericField("name", setOf("test", "anotherTest")),
            GenericField("title", "Peter Pan"),
            GenericField("author", authorRecord1)
        ))
        val doc2 = GenericDocument("book","test",setOf(
            GenericField("name", setOf("test", "anotherTest")),
            GenericField("title", "Peter Pan"),
            GenericField("author", authorRecord1)
        ))

        assertEquals(doc1, doc2)
    }

    @Test
    fun equals_documentsWithRecordArray_isTrue() {
        val authorRecords1 = setOf(
            GenericRecord("author", setOf(
                GenericField("name", "J. M. Barrie"),
                GenericField("date_of_birth", "May 9, 1860"))),
            GenericRecord("author", setOf(
                    GenericField("name", "Bob"),
                    GenericField("date_of_birth", "May 9, 2014")))
        )
        val authorRecords2 = setOf(
            GenericRecord("author", setOf(
                GenericField("name", "J. M. Barrie"),
                GenericField("date_of_birth", "May 9, 1860"))),
            GenericRecord("author", setOf(
                GenericField("name", "Bob"),
                GenericField("date_of_birth", "May 9, 2014")))
        )

        val doc1 = GenericDocument("book","test", setOf(
            GenericField("name", setOf("test", "anotherTest")),
            GenericField("title", "Peter Pan"),
            GenericField("author", authorRecords1)
        ))
        val doc2 = GenericDocument("book","test", setOf(
            GenericField("name", setOf("test", "anotherTest")),
            GenericField("title", "Peter Pan"),
            GenericField("author", authorRecords1)
        ))

        assertEquals(doc1, doc2)
    }
}
