package io.datalbry.precise.api.validation

import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.document.Document
import java.io.File

/**
 * [DocumentValidator] validates [Document] using their [Schema] definition
 *
 * @author timo gruen - 2021-03-17
 */
interface DocumentValidator {

    /**
     * Checks if the [document] is valid according to the [schema]
     *
     * @param schema containing the document definition
     * @param document to validate
     *
     * @return true if the document is valid, otherwise false
     */
    fun isValid(schema: Schema, document: Document): Boolean

    /**
     * Checks if the [file] contains a valid [Document], which is valid according to the [schema]
     *
     * @param schema containing the document definition
     * @param file containing the [Document]
     *
     * @return true if the document is valid, otherwise false
     */
    fun isValid(schema: Schema, file: File): Boolean

}
