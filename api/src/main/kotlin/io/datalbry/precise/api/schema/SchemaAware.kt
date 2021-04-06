package io.datalbry.precise.api.schema

/**
 * Objects annotated with [SchemaAware] are being used for schema generation by Precise
 *
 * [SchemaAware] annotated classes are taken into account for the Schema generation
 * Properties of such classes might only contain be of well-known types or other [SchemaAware] annotated types
 *
 * @see [io.datalbry.precise.api.schema.field.BasicFieldType] for supported primitive types
 *
 * @author timo gruen - 2021-03-11
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class SchemaAware
