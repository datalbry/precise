package io.datalbry.precise.processor.kotlin.extension

import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration

/**
 * Kotlin uses dedicated types for unboxed primitive arrays.
 * As those are not marked of type Array<*> we have to handle them independently
 *
 * Below are all currently known primitive array types
 */
val PRIMITIVE_ARRAY_TYPES = arrayOf(
    "IntArray",
    "ByteArray",
    "CharArray",
    "ShortArray",
    "LongArray",
    "FloatArray",
    "DoubleArray",
    "BooleanArray"
)

/**
 * Mappings of primitive arrays to their primitive types
 */
val PRIMITIVE_ARRAY_TYPES_MAPPING = mapOf(
    "IntArray" to "int",
    "ByteArray" to "byte",
    "CharArray" to "char",
    "ShortArray" to "int",
    "LongArray" to "long",
    "FloatArray" to "float",
    "DoubleArray" to "double",
    "BooleanArray" to "boolean"
)

/**
 * Declaration name of arrays
 */
const val ARRAY_DECLARATION_NAME = "Array"

/**
 * Declaration name of optionals
 * TODO: Add package (as they are in java.util.*)
 */
const val OPTIONAL_DECLARATION_NAME = "Optional"

fun KSPropertyDeclaration.isArray(): Boolean {
    return this.type.resolve().declaration.simpleName.asString() == ARRAY_DECLARATION_NAME
}

fun KSPropertyDeclaration.getInnerType(): KSDeclaration {
    return this.type.resolve().arguments[0].type!!.resolve().declaration
}

fun KSPropertyDeclaration.isPrimitiveArray(): Boolean {
    val type = this.type.resolve().declaration.simpleName.asString()
    return PRIMITIVE_ARRAY_TYPES.contains(type)
}

fun KSPropertyDeclaration.getInnerTypeNameOfPrimitiveArray(): String {
    val type = this.type.resolve().declaration.simpleName.asString()
    return PRIMITIVE_ARRAY_TYPES_MAPPING[type]!!
}

fun KSPropertyDeclaration.isOptional(): Boolean {
    return this.type.resolve().declaration.simpleName.asString() == OPTIONAL_DECLARATION_NAME
}

fun KSPropertyDeclaration.isNullable(): Boolean {
    return this.type.resolve().isMarkedNullable
}
