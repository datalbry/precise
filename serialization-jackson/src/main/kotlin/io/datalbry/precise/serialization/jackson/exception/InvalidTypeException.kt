package io.datalbry.precise.serialization.jackson.exception

import io.datalbry.precise.api.schema.type.Type

class InvalidTypeException(type: Type, awaitedType: Class<out Type>): IllegalArgumentException(
    "Type is defined as [${type::class.java.simpleName}] but requires to be [${awaitedType.simpleName}]")
