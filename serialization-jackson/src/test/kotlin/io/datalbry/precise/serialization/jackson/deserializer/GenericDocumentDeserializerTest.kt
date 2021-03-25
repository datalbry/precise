package io.datalbry.precise.serialization.jackson.deserializer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.document.Document
import io.datalbry.precise.api.schema.document.Record
import io.datalbry.precise.serialization.jackson.deserializer.assertion.assertValueType
import io.datalbry.precise.serialization.jackson.deserializer.util.getTestDocument
import io.datalbry.precise.serialization.jackson.deserializer.util.getTestSchema
import org.junit.jupiter.api.Test

internal class GenericDocumentDeserializerTest {

    @Test
    fun deserialize_simpleDocument_allFieldsArePresent() {
        val schema = getTestSchema("Company.json")
        val json = getTestDocument("Company.json")
        val jackson = getJacksonMapper(schema)

        val document: Document = jackson.readValue(json)
        val presentKeys = document.getKeys()
        presentKeys.contains("name")
        presentKeys.contains("homepage")
        presentKeys.contains("location")

        assertValueType<String>(document["name"])
        assertValueType<String>(document["homepage"])
        assertValueType<String>(document["location"])
    }

    @Test
    fun deserialize_nestedDocuments_allFieldsArePresent() {
        val schema = getTestSchema("Employee.json")
        val json = getTestDocument("Employee.json")
        val jackson = getJacksonMapper(schema)

        val document: Document = jackson.readValue(json)
        val presentKeys = document.getKeys()
        presentKeys.contains("name")
        presentKeys.contains("lastName")
        presentKeys.contains("company")

        assertValueType<String>(document["name"])
        assertValueType<String>(document["lastName"])
        assertValueType<Record>(document["company"])

        val company = document["company"].value as Record
        assertValueType<String>(company["name"])
        assertValueType<String>(company["homepage"])
        assertValueType<String>(company["location"])
    }

    @Test
    fun deserialize_documentWithMultiValueFields_multiValueFieldAreDeserializedCorrectly() {
        val schema = getTestSchema("Book.json")
        val json = getTestDocument("Book.json")
        val jackson = getJacksonMapper(schema)

        val document: Document = jackson.readValue(json)
        val presentKeys = document.getKeys()
        presentKeys.contains("title")
        presentKeys.contains("description")
        presentKeys.contains("author")
        presentKeys.contains("label")

        assertValueType<String>(document["title"])
        assertValueType<String>(document["description"])
        assertValueType<Collection<String>>(document["label"])
        assertValueType<Record>(document["author"])
        assertValueType<Collection<Record>>(document["co-authors"])
    }

    private fun getJacksonMapper(schema: Schema): ObjectMapper {
        val jackson = jacksonObjectMapper()
        val module = SimpleModule().addDeserializer(Document::class.java, GenericDocumentDeserializer(schema))
        jackson.registerModule(module)
        return jackson
    }

}
