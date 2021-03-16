package io.datalbry.precise.serialization.jackson.document

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.document.Document
import io.datalbry.precise.serialization.jackson.document.util.getTestDocument
import io.datalbry.precise.serialization.jackson.document.util.getTestSchema
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GenericDocumentDeserializerTest {

    @Test
    fun deserialize_simpleDocument_allFieldsArePresent() {
        val schema = getTestSchema<GenericDocumentDeserializer>("Company.json")
    }

    @Test
    fun deserialize_nestedDocuments_allFieldsArePresent() {
        val schema = getTestSchema<GenericDocumentDeserializer>("Employee.json")
        val json = getTestDocument<GenericDocumentDeserializer>("Employee.json")
        val jackson = getJacksonMapper(schema)

        val document: Document = jackson.readValue(json)
        TODO()
    }



    private fun getJacksonMapper(schema: Schema): ObjectMapper {
        val jackson = jacksonObjectMapper()
        val module = SimpleModule().addDeserializer(Document::class.java, GenericDocumentDeserializer(schema))
        jackson.registerModule(module)
        return jackson
    }

}
