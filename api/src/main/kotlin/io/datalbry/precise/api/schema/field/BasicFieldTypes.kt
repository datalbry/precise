package io.datalbry.precise.api.schema.field

/**
 * [BasicFieldTypes] contains any "primitive" types supported by Precise.
 *
 * @param id of the type. Used by [Field.type]
 *
 * @author timo gruen - 2021-03-09
 */
enum class BasicFieldTypes(val id: String) {
    /**
     * no value
     */
    NULL("null"),

    /**
     * 32-bit signed integer
     */
    INT("int"),

    /**
     * 64-bit signed integer
     */
    LONG("long"),

    /**
     * single-precision (32-bit) IEEE 754 floating-point number
     */
    FLOAT("float"),

    /**
     * double-precision (64-bit) IEEE 754 floating-point number
     */
    DOUBLE("double"),

    /**
     * sequence of 8-bit unsigned bytes
     */
    BYTE("byte"),

    /**
     * unicode character sequence
     */
    STRING("string"),

    /**
     * represents the two truth values of Boolean algebra.
     * Valid: [true, false]
     */
    BOOLEAN("boolean")
}
