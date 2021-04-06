package io.datalbry.precise.api.schema

/**
 * Properties annotated with [Exclude] are being excluded for schema generation
 *
 * Only valid for [SchemaAware] annotated classes
 *
 * @see [SchemaAware]
 *
 * @author timo gruen - 2021-04-06
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class Exclude
