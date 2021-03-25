package io.datalbry.precise.serialization.jackson.exception

class SchemaTypeNotFound(type: String): IllegalArgumentException(
    "Schema does not contain a definition for Type $type")
