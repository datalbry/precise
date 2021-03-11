package io.datalbry.precise.processor.kotlin

import io.datalbry.precise.processor.kotlin.assertion.assertSameContent
import io.datalbry.precise.processor.kotlin.util.compile
import io.datalbry.precise.processor.kotlin.util.getSchemaFile
import io.datalbry.precise.processor.kotlin.util.getTestSchema
import org.junit.jupiter.api.Test

/*
 * Proceed with caution (!)
 *
 * All tests are using a raw class file which is not classpath aware in the first place.
 * All those class files are compiled using a classpath aware compiler - but (!) in this particular case,
 * your IDE won't be able to help you with class insights and it won't be able to refactor e.g. imports automatically
 *
 * Especially when seeing the following issue:
 * ---
 * Collection contains no element matching the predicate.
 * java.util.NoSuchElementException: Collection contains no element matching the predicate.
 * ---
 *
 * consider checking the imports of [getSimpleItem] and the entire class definitions in that particular file.
 *
 * There is another bug directly related to AdoptOpen JDK 1.8.275
 * ---
 * java/nio/file/FileSystem.supportedFileAttributeViews()java/util/Set;
 * java.lang.AbstractMethodError: java/nio/file/FileSystem
 * ---
 *
 * To run the test, please use AdoptOpen JDK 1.11 or Amazon JDK 1.8.275
 */
internal class SchemaAwareProcessorTest {

    @Test
    fun process_simpleItem_worksJustFine() {
        val compiledSchema = compile(simpleItem).getSchemaFile()
        val testSchema = getTestSchema<SchemaAwareProcessor>("SimpleItem.json")
        assertSameContent(compiledSchema, testSchema)
    }



}
