package io.datalbry.precise.processor.kotlin.extension

import com.google.devtools.ksp.processing.Resolver
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
 * Declaration name of [java.util.Optional]
 */
const val OPTIONAL_DECLARATION_NAME = "Optional"

/**
 * Declaration package of [java.util.Optional]
 */
const val OPTIONAL_DECLARATION_PACKAGE = "java.util"

/**
 * Declaration of [kotlin.collections.Collection]
 */
const val COLLECTION_DECLARATION = "kotlin.collections.Collection"

/**
 * Short circuit to check if the type is a array
 *
 * - Important -
 * Returns false for primitive arrays, such as [IntArray] while [Array] of [Int] returns true
 * For more information regarding primitive (unboxed) arrays checkout [https://kotlinlang.org/docs/basic-types.html#primitive-type-arrays](KotlinDoc)
 *
 * @see isPrimitiveArray for primitive arrays
 *
 * @return true if the type is array, else false
 */
fun KSPropertyDeclaration.isArray(): Boolean {
    return this.type.resolve().declaration.simpleName.asString() == ARRAY_DECLARATION_NAME
}

/**
 * Short circuit to get the KSDeclaration of a generic with exactly one type
 *
 * @throws AssertionError if the there are more than one generic argument
 *
 * @return [KSDeclaration] of the inner type
 */
fun KSPropertyDeclaration.getInnerType(): KSDeclaration {
    val arguments = this.type.resolve().arguments
    assert(arguments.size == 1)
    return arguments[0].type!!.resolve().declaration
}

/**
 * Short circuit to check if the [KSPropertyDeclaration] is a primitive array
 *
 * - Important -
 * Returns true for primitive arrays, such as [IntArray] while [Array] of [Int] returns false
 * For more information regarding primitive (unboxed) arrays checkout [https://kotlinlang.org/docs/basic-types.html#primitive-type-arrays](KotlinDoc)
 *
 * @see isArray for non-primitive arrays
 *
 * @return true if the [KSPropertyDeclaration] is a primitive array, else false
 */
fun KSPropertyDeclaration.isPrimitiveArray(): Boolean {
    val type = this.type.resolve().declaration.simpleName.asString()
    return PRIMITIVE_ARRAY_TYPES.contains(type)
}

/**
 * Short circuit to get the primitive type of the primitive array
 *
 * @sample "int" for IntArray,
 * @sample "byte" for ByteArray
 * ...
 *
 * @return int for IntArray
 */
fun KSPropertyDeclaration.getInnerTypeNameOfPrimitiveArray(): String {
    val type = this.type.resolve().declaration.simpleName.asString()
    return PRIMITIVE_ARRAY_TYPES_MAPPING[type]!!
}

/**
 * Short circuit to check if the type of the [KSPropertyDeclaration] is [java.util.Optional]
 *
 * @return true if the type is [java.util.Optional], else false
 */
fun KSPropertyDeclaration.isOptional(): Boolean {
    val declaration = this.type.resolve().declaration
    return declaration.hasName(OPTIONAL_DECLARATION_NAME) && declaration.hasPackage(OPTIONAL_DECLARATION_PACKAGE)
}

/**
 * Short circuit to check if the type of [KSPropertyDeclaration] is of (super) type [Collection]
 *
 * @return true if the type is a collection, else false
 */
fun KSPropertyDeclaration.isCollection(resolver: Resolver): Boolean {
    val ksName = resolver.getKSNameFromString(COLLECTION_DECLARATION)
    val collectionDeclaration = resolver.getClassDeclarationByName(ksName)
    return collectionDeclaration!!.asStarProjectedType().isAssignableFrom(this.type.resolve())
}

/**
 * Short circuit to check if the [KSPropertyDeclaration] is marked nullable
 *
 * @return true if its nullable, else false
 */
fun KSPropertyDeclaration.isNullable(): Boolean {
    return this.type.resolve().isMarkedNullable
}
