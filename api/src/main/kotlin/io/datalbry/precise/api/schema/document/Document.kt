package io.datalbry.precise.api.schema.document

/**
 * [Document] holds a set of fields describing the actual document
 *
 * @author timo gruen - 2021-03-15
 */
interface Document: Record {

    /**
     * Type of the document
     *
     * The type has to be defined in the corresponding schema
     */
    val type: String
}
