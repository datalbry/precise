package io.datalbry.precise.api.schema.type

/**
 * Basic interface for any [io.datalbry.precise.api.schema.Schema] type
 *
 * [Type] might only be internally extended and not externally
 *
 * Currently supported:
 * - [DocumentType]
 * - [EnumType]
 *
 * !!! IMPORTANT !!!
 * Never extend the interface manually, as the Precise framework only supports different predefined types
 *
 * @author timo gruen - 2021-03-11
 */
interface Type {
    val name: String
    val type: Types
}
