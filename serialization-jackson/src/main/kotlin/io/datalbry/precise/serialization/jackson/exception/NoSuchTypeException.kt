package io.datalbry.precise.serialization.jackson.exception

import io.datalbry.precise.api.schema.Schema

class NoSuchTypeException(schema: Schema, type: String): IllegalArgumentException(
    "Type[$type] is not present in Schema. Schema only contains the following types ${getDefinedTypeNames(schema)}")

private fun getDefinedTypeNames(schema: Schema) = schema.types.map { it.name }.toSet()
