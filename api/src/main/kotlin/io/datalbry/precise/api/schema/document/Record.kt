package io.datalbry.precise.api.schema.document

/**
 * A [Record] is basically a wrapper around a set of fields without type information in place
 *
 * Records are never top-level entities of a schema, but are especially helpful to get
 *
 * @author timo gruen - 2021-03-16
 */
interface Record {

    /**
     * Get a specific field of the record
     *
     * @param key of the field
     *
     * @return field with matching [key]
     */
    fun get(key: String): Field<*>

    /**
     * Get all present field keys of the record
     *
     * Note: Optional field keys might be missing, depending on whether they are present in the record, or not
     *
     * @return Set of all keys
     */
    fun getKeys(): Set<String>

}
