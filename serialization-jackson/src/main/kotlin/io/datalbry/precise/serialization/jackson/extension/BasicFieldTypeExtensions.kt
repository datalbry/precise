package io.datalbry.precise.serialization.jackson.extension

import io.datalbry.precise.api.schema.field.BasicFieldType

fun basicFieldTypeById(id: String) = BasicFieldType.values().first { it.id == id }

fun isBasicFieldType(id: String) = BasicFieldType.values().any { it.id == id }
