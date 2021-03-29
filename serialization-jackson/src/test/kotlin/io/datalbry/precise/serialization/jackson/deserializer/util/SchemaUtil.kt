package io.datalbry.precise.serialization.jackson.deserializer.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.serialization.jackson.deserializer.SchemaDeserializer
import java.io.File
import java.nio.file.Paths

val schemaModule: SimpleModule = SimpleModule().addDeserializer(Schema::class.java, SchemaDeserializer())
val jackson: ObjectMapper = jacksonObjectMapper().registerModule(schemaModule)

fun Any.getTestSchema(name: String): Schema {
    val resource = this::class.java.getResource("/schema/${name}")
    val file = Paths.get(resource.toURI()).toFile()
    return jackson.readValue(file)
}

fun Any.getTestDocument(name: String): File {
    val resource = this::class.java.getResource("/document/${name}")
    return Paths.get(resource.toURI()).toFile()
}
