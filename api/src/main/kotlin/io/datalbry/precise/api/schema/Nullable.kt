package io.datalbry.precise.api.schema

/**
 * Properties annotated with [Nullable] are being marked optional in the derived Precise Schema
 *
 * Only valid for [SchemaAware] annotated classes
 *
 * @see [SchemaAware]
 *
 * @author timo gruen - 2021-04-14
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER)
annotation class Nullable
