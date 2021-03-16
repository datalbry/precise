package io.datalbry.precise.serialization.jackson.schema.exception

class SchemaTypeNotFound(type: String): IllegalArgumentException(
    "Schema does not contain a definition for for Type $type")
