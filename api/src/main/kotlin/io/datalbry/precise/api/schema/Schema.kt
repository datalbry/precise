package io.datalbry.precise.api.schema

import io.datalbry.precise.api.schema.type.Type

/**
 * The [Schema] stores type information which can be consumed by Precise.
 *
 * Precise is using the Schema for serialization and deserialization.
 *
 * @param types of the [Schema]
 *
 * @author timo gruen - 2021-03-11
 */
data class Schema(
    val types: Set<Type>
)
