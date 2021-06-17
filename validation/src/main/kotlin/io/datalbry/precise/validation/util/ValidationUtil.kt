package io.datalbry.precise.validation.util

import io.datalbry.precise.api.schema.field.BasicFieldType

/**
 * Obtain a basic field type from a given value
 *
 * @param value to obtain basic field type from
 * @return basic field type if applicable, or null
 */
fun basicFieldTypeByValue(value: Any?): BasicFieldType? = when (value) {
    is Boolean -> BasicFieldType.BOOLEAN
    is String -> BasicFieldType.STRING
    is Byte -> BasicFieldType.BYTE
    is Double -> BasicFieldType.DOUBLE
    is Float -> BasicFieldType.FLOAT
    is Long -> BasicFieldType.LONG
    is Int -> BasicFieldType.INT
    else -> null
}
