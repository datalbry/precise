package io.datalbry.precise.api.schema.document

/**
 * [Field] holds value of a generic type [T]
 *
 * @param name of the field
 * @param value of the field
 *
 * @author timo gruen - 2021-03-15
 */
interface Field<T> {
    val name: String
    val value: T
}
