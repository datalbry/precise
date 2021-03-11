package io.datalbry.precise.processor.kotlin.assertion

import org.apache.commons.io.FileUtils
import org.junit.jupiter.api.Assertions
import java.io.File
import java.nio.charset.StandardCharsets

fun assertSameContent(file1: File, file2: File) {
    Assertions.assertTrue {
        FileUtils.contentEqualsIgnoreEOL(
            file1,
            file2,
            StandardCharsets.UTF_8.name()
        )
    }
}
