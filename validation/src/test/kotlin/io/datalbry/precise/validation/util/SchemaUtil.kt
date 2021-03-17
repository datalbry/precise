package io.datalbry.precise.validation.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.serialization.jackson.schema.SchemaDeserializer
import java.io.File
import java.nio.file.Paths

val schemaModule: SimpleModule = SimpleModule().addDeserializer(Schema::class.java, SchemaDeserializer())
val jackson: ObjectMapper = jacksonObjectMapper().registerModule(schemaModule)

inline fun <reified T> getTestSchema(name: String): Schema {
    val resource = T::class.java.getResource("/schema/${name}")
    val file = Paths.get(resource.toURI()).toFile()
    return jackson.readValue(file)
}

inline fun <reified T> getTestDocument(name: String): File {
    val resource = T::class.java.getResource("/document/${name}")
    return Paths.get(resource.toURI()).toFile()
}
