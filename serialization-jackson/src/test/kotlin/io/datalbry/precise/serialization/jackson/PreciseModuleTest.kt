package io.datalbry.precise.serialization.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.document.Document
import io.datalbry.precise.api.schema.document.generic.GenericDocument
import io.datalbry.precise.api.schema.document.generic.GenericField
import io.datalbry.precise.api.schema.document.generic.GenericRecord
import io.datalbry.precise.serialization.jackson.deserializer.assertion.assertContainsValues
import io.datalbry.precise.serialization.jackson.deserializer.util.getTestDocument
import io.datalbry.precise.serialization.jackson.deserializer.util.getTestSchema
import io.datalbry.precise.serialization.jackson.extension.getEnumType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PreciseModuleTest {

    @Test
    fun deserialize_simpleDocument_allFieldsArePresent() {
        val schema = getTestSchema("Company.json")
        val jackson = preconfiguredJackson(schema)

        val doc1 = GenericDocument("Company","datalbry", setOf(
            GenericField("name", "DataLbry"),
            GenericField("homepage", "datalbry.io"),
            GenericField("location", "de_DE")
        ))

        val docString = jackson.writeValueAsString(doc1)
        val doc2 = jackson.readValue<Document>(docString)

        assertEquals(doc1, doc2)
    }

    @Test
    fun deserialize_documentWithNestedRecord_allFieldsArePresent() {
        val schema = getTestSchema("Book.json")
        val jackson = preconfiguredJackson(schema)

        val doc1 = GenericDocument("Book","TestBook", setOf(
            GenericField("title", "Biography"),
            GenericField("description", "The Biography of someone!"),
            GenericField("label", setOf("Biography", "DataLbry")),
            GenericField("author", GenericRecord("Person", setOf(
                GenericField("name", "timo gruen"),
                GenericField("email", "timo.gruen@datalbry.io")))),
            GenericField("co-authors", setOf(
                GenericRecord("Person", setOf(
                    GenericField("name", "zeljko bekcic"),
                    GenericField("email", "zeljko.bekcic@datalbry.io"))),
                GenericRecord("Person", setOf(
                    GenericField("name", "anonymous"),
                    GenericField("email", "anonymous@datalbry.io")))))))

        val docString = jackson.writeValueAsString(doc1)
        val doc2 = jackson.readValue<Document>(docString)

        assertContainsValues(doc2["label"], doc1["label"].value as Collection<*>)
        assertContainsValues(doc2["co-authors"], doc1["co-authors"].value as Collection<*>)
    }

    @Test
    fun deserialize_schemaWithEnum_worksJustFine() {
        val schema = getTestSchema("Person.json")
        val enumSchema = schema.getEnumType("PositionEnum")
        assertEquals(enumSchema.values.toSet(), setOf("CEO", "Dev", "Sales", "Other"))
    }

    private fun preconfiguredJackson(schema: Schema): ObjectMapper {
        val jackson = jacksonObjectMapper()
        jackson.registerModule(PreciseModule(schema))
        return jackson
    }

}
