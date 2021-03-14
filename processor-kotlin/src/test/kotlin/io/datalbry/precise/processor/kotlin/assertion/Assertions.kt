package io.datalbry.precise.processor.kotlin.assertion

import org.junit.jupiter.api.Assertions
import java.io.File
import java.nio.charset.StandardCharsets

fun assertSameContent(file1: File, file2: File) {
    val content1 = file1.readText(StandardCharsets.UTF_8).trimIndent()
    val content2 = file2.readText(StandardCharsets.UTF_8).trimIndent()
    Assertions.assertEquals(content1, content2)
}
