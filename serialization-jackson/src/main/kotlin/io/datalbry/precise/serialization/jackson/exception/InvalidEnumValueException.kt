package io.datalbry.precise.serialization.jackson.exception

import io.datalbry.precise.api.schema.type.EnumType

class InvalidEnumValueException(type: EnumType, value: String) :
    IllegalArgumentException("Value \"${value}\" is not contained by type \"${type.name}\" with values: ${type.values}")