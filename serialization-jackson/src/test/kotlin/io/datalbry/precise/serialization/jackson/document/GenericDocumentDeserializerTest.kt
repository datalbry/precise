package io.datalbry.precise.serialization.jackson.document

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.document.Document
import io.datalbry.precise.api.schema.document.Record
import io.datalbry.precise.serialization.jackson.document.assertion.assertValueType
import io.datalbry.precise.serialization.jackson.document.util.getTestDocument
import io.datalbry.precise.serialization.jackson.document.util.getTestSchema
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GenericDocumentDeserializerTest {

    @Test
    fun deserialize_simpleDocument_allFieldsArePresent() {
        val schema = getTestSchema<GenericDocumentDeserializer>("Company.json")
        val json = getTestDocument<GenericDocumentDeserializer>("Company.json")
        val jackson = getJacksonMapper(schema)

        val document: Document = jackson.readValue(json)
        val presentKeys = document.getKeys()
        presentKeys.contains("name")
        presentKeys.contains("homepage")
        presentKeys.contains("location")

        assertValueType<String>(document.get("name"))
        assertValueType<String>(document.get("homepage"))
        assertValueType<String>(document.get("location"))
    }

    @Test
    fun deserialize_nestedDocuments_allFieldsArePresent() {
        val schema = getTestSchema<GenericDocumentDeserializer>("Employee.json")
        val json = getTestDocument<GenericDocumentDeserializer>("Employee.json")
        val jackson = getJacksonMapper(schema)

        val document: Document = jackson.readValue(json)
        val presentKeys = document.getKeys()
        presentKeys.contains("name")
        presentKeys.contains("lastName")
        presentKeys.contains("company")

        assertValueType<String>(document.get("name"))
        assertValueType<String>(document.get("lastName"))
        assertValueType<Record>(document.get("company"))

        val company = document.get("company").value as Record
        assertValueType<String>(company.get("name"))
        assertValueType<String>(company.get("homepage"))
        assertValueType<String>(company.get("location"))
    }

    @Test
    fun deserialize_documentWithMultiValueFields_multiValueFieldAreDeserializedCorrectly() {
        val schema = getTestSchema<GenericDocumentDeserializer>("Book.json")
        val json = getTestDocument<GenericDocumentDeserializer>("Book.json")
        val jackson = getJacksonMapper(schema)

        val document: Document = jackson.readValue(json)
        val presentKeys = document.getKeys()
        presentKeys.contains("title")
        presentKeys.contains("description")
        presentKeys.contains("author")
        presentKeys.contains("label")

        assertValueType<String>(document.get("title"))
        assertValueType<String>(document.get("description"))
        assertValueType<Collection<String>>(document.get("label"))
        assertValueType<Record>(document.get("author"))
        assertValueType<Collection<Record>>(document.get("co-authors"))
    }

    private fun getJacksonMapper(schema: Schema): ObjectMapper {
        val jackson = jacksonObjectMapper()
        val module = SimpleModule().addDeserializer(Document::class.java, GenericDocumentDeserializer(schema))
        jackson.registerModule(module)
        return jackson
    }

}
