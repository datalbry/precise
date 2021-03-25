package io.datalbry.precise.api.schema.document

/**
 * [Field] holds value of a generic type [ValueType]
 *
 * @param ValueType of the value, the value might be any [io.datalbry.precise.api.schema.field.BasicFieldType]
 *  or any defined type present in the [io.datalbry.precise.api.schema.Schema]
 *
 * @author timo gruen - 2021-03-15
 */
interface Field<ValueType> {
    /**
     * name of the field
     *
     * The name is being used as a identifier in the schema, taking the entire path into account
     *
     * e.g.
     * {
     *   "author": {
     *     "name": "Timo"
     *     "email": "no-reply@datalbry.io"
     *   }
     * }
     *
     */
    val name: String

    /**
     * Generic Value of the field
     */
    val value: ValueType
}
