package io.datalbry.precise.processor.kotlin

import io.datalbry.precise.processor.kotlin.assertion.assertSameContent
import io.datalbry.precise.processor.kotlin.items.*
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
        val compiledSchema = compile(itemWithOnePrimitiveType).getSchemaFile()
        val testSchema = getTestSchema<SchemaAwareProcessor>("SimpleItem.json")
        assertSameContent(compiledSchema, testSchema)
    }

    @Test
    fun process_enumItem_worksJustFine() {
        val compiledSchema = compile(enumItem).getSchemaFile()
        val testSchema = getTestSchema<SchemaAwareProcessor>("EnumItem.json")
        assertSameContent(compiledSchema, testSchema)
    }

    @Test
    fun process_item_WithMultiplePrimitiveTypes() {
        val compiledSchema = compile(itemWithMultiplePrimitiveTypes).getSchemaFile()
        val testSchema = getTestSchema<SchemaAwareProcessor>("ItemWithMultiplePrimitiveTypes.json")
        assertSameContent(compiledSchema, testSchema)
    }

    @Test
    fun process_item_withArrayField() {
        val compiledSchema = compile(itemWithArrayType).getSchemaFile()
        val testSchema = getTestSchema<SchemaAwareProcessor>("ItemWithArrayType.json")
        assertSameContent(compiledSchema, testSchema)
    }

    @Test
    fun process_item_withPrimitiveArrayField() {
        val compiledSchema = compile(itemWithPrimitiveArrayType).getSchemaFile()
        val testSchema = getTestSchema<SchemaAwareProcessor>("ItemWithPrimitiveArrayType.json")
        assertSameContent(compiledSchema, testSchema)
    }

    @Test
    fun process_item_withNullableField() {
        val compiledSchema = compile(itemWithNullableType).getSchemaFile()
        val testSchema = getTestSchema<SchemaAwareProcessor>("ItemWithNullableType.json")
        assertSameContent(compiledSchema, testSchema)
    }

    @Test
    fun process_item_withOptionalType() {
        val compiledSchema = compile(itemWithOptionalType).getSchemaFile()
        val testSchema = getTestSchema<SchemaAwareProcessor>("ItemWithOptionalType.json")
        assertSameContent(compiledSchema, testSchema)
    }

    @Test
    fun process_multipleItems_getsAggregated() {
        val compiledSchema = compile(itemWithOnePrimitiveType, itemWithOptionalType, itemWithArrayType).getSchemaFile()
        val testSchema = getTestSchema<SchemaAwareProcessor>("AggregatedItems.json")
        assertSameContent(compiledSchema, testSchema)
    }

    @Test
    fun process_multipleItems_withCrossLinkingFields() {
        val compiledSchema = compile(itemWithCrossLinkToAuthor, innerTypeAuthor).getSchemaFile()
        val testSchema = getTestSchema<SchemaAwareProcessor>("ItemWithInnerTypeAuthor.json")
        assertSameContent(compiledSchema, testSchema)
    }
}
