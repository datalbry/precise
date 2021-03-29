package io.datalbry.precise.api.schema.document

/**
 * [Document] holds a set of fields describing the actual document
 *
 * @author timo gruen - 2021-03-15
 */
interface Document: Record {

    /**
     * The id of the document
     *
     * IDs have to be globally unique, as they directly identify a specific [Document]
     */
    val id: String

}
