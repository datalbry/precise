package io.datalbry.precise.api.validation

import io.datalbry.precise.api.schema.Schema
import java.io.File

/**
 * [Validator] is able to validate a instance of type [Type]
 *
 * The validator requires a schema to check whether an instance of [Type] is valid or not
 */
interface Validator<Type> {

    /**
     * Checks if the [Type] is valid according to the [schema]
     *
     * @param schema containing the document definition
     * @param type to validate
     *
     * @return true if the document is valid, otherwise false
     */
    fun isValid(schema: Schema, type: Type): Boolean

    /**
     * Checks if the [file] contains a valid [Type], which is valid according to the [schema]
     *
     * @param schema containing the document definition
     * @param file containing the [Type]
     *
     * @return true if the document is valid, otherwise false
     */
    fun isValid(schema: Schema, file: File): Boolean

}
