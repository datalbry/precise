package io.datalbry.precise.validation.util

import io.datalbry.precise.api.schema.field.BasicFieldType
import io.datalbry.precise.api.schema.field.Field
import io.datalbry.precise.api.schema.type.EnumType
import io.datalbry.precise.validation.extensions.basicFieldTypeById

/**
 * Checks if the given [value] is a valid Enum value
 *
 * @param type definition of the enum
 * @param value to check
 *
 * @return true if the [value] is a valid enum value
 */
fun isValidEnum(type: EnumType, value: Any?): Boolean {
    return value is String && type.values.contains(value)
}

/**
 * Checks if the given [value] is described by the [field] definition
 *
 * @param field definition
 * @param value to check
 *
 * @return true if the [value] is being described by the [field]
 */
fun isValidBasicType(field: Field, value: Any?): Boolean {
    val type = basicFieldTypeById(field.type)
    return validateBasicType(type, value)
}

/**
 * Validates if the given [value] has the correct type
 *
 * @sample value="SomeString" and type=BasicFieldType.STRING it will return true
 * @sample value=10 and type=BasicFieldType.STRING it will return false
 *
 * @return true if the value has the correct java type, else false
 */
fun validateBasicType(type: BasicFieldType, value: Any?): Boolean = when (type) {
    BasicFieldType.INT -> value is Int
    BasicFieldType.LONG -> value is Long
    BasicFieldType.FLOAT -> value is Float
    BasicFieldType.DOUBLE -> value is Double
    BasicFieldType.BYTE -> value is Byte
    BasicFieldType.STRING -> value is String
    BasicFieldType.BOOLEAN -> value is Boolean
}
