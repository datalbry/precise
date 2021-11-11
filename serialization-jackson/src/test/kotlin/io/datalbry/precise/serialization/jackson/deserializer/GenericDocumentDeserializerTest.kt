package io.datalbry.precise.serialization.jackson.deserializer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.document.Document
import io.datalbry.precise.api.schema.document.Record
import io.datalbry.precise.api.schema.document.generic.GenericField
import io.datalbry.precise.api.schema.document.generic.GenericRecord
import io.datalbry.precise.serialization.jackson.deserializer.assertion.assertContainsValues
import io.datalbry.precise.serialization.jackson.deserializer.assertion.assertFieldPresent
import io.datalbry.precise.serialization.jackson.deserializer.assertion.assertValueType
import io.datalbry.precise.serialization.jackson.deserializer.util.getTestDocument
import io.datalbry.precise.serialization.jackson.deserializer.util.getTestSchema
import org.junit.jupiter.api.Assertions.assertTrue
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

        val bob = GenericRecord("Person", setOf(
            GenericField("name", "bob"),
            GenericField("email", "bob@datalbry.io")
        ))

        val alice = GenericRecord("Person", setOf(
            GenericField("name", "alice"),
            GenericField("email", "alice@datalbry.io")
        ))
        assertContainsValues(document["co-authors"], alice, bob)
        assertContainsValues(document["label"], "Fantasy", "Romance", "Anakin")
    }

    @Test
    fun deserialize_documentWithMultipleRecords_worksJustFine() {
        val schema = getTestSchema("Space.json")
        val json = getTestDocument("Space.json")
        val jackson = getJacksonMapper(schema)

        val document: Document = jackson.readValue(json)
        assertFieldPresent("key", document)
        assertFieldPresent("createdDate", document)
        assertFieldPresent("name", document)
        assertFieldPresent("spaceId", document)
        assertFieldPresent("createdBy", document)
        assertFieldPresent("icon", document)

        assertValueType<String>(document["key"])
        assertValueType<String>(document["createdDate"])
        assertValueType<String>(document["name"])
        assertValueType<String>(document["spaceId"])
        assertValueType<Record>(document["createdBy"])
    }

    @Test
    fun deserialize_documentWithEnum_worksJustFine() {
        val schema = getTestSchema("Person.json")
        val json = getTestDocument("Person.json")
        val jackson = getJacksonMapper(schema)

        val document: Document = jackson.readValue(json)

        val presentKeys = document.getKeys()
        presentKeys.contains("name")
        presentKeys.contains("lastName")
        presentKeys.contains("company")
        presentKeys.contains("position")

        assertValueType<String>(document["name"])
        assertValueType<String>(document["lastName"])
        assertValueType<String>(document["company"])
        assertValueType<String>(document["position"])
    }

    private fun getJacksonMapper(schema: Schema): ObjectMapper {
        val jackson = jacksonObjectMapper()
        val module = SimpleModule().addDeserializer(Document::class.java, GenericDocumentDeserializer(schema))
        jackson.registerModule(module)
        return jackson
    }

}
