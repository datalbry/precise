package io.datalbry.precise.processor.kotlin.visitor

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.visitor.KSEmptyVisitor
import io.datalbry.precise.api.schema.field.BasicFieldType
import io.datalbry.precise.api.schema.field.Field
import io.datalbry.precise.api.schema.type.DocumentType
import io.datalbry.precise.api.schema.type.EnumType
import io.datalbry.precise.api.schema.type.Type
import io.datalbry.precise.api.schema.type.Types
import io.datalbry.precise.processor.kotlin.extension.*

/**
 * [KSPropertyDeclaration] implementation which searches for [Type]s
 *
 * @author timo gruen - 2021-03-11
 */
class FindTypesVisitor(
    private val logger: KSPLogger,
    private val resolver: Resolver
): KSEmptyVisitor<Unit, Set<Type>>() {
    private val typeVisitor = FindTypeVisitor()
    private val fieldVisitor = FindFieldVisitor()
    private val valueVisitor = FindEnumValueVisitor()

    override fun defaultHandler(node: KSNode, data: Unit): Set<Type> {
        logger.info("Start processing", node)
        val typeInformation = node.accept(typeVisitor, Unit)
        return when (typeInformation.type) {
            Types.DOCUMENT -> {
                logger.info("Start searching for fields", node)
                val fields = node.accept(fieldVisitor, Unit)
                val type = DocumentType(typeInformation.name, fields.map(this::toField).toSet())
                setOf(type)
            }
            Types.ENUM -> {
                logger.info("Start searching for enum values", node)
                val values = node.accept(valueVisitor, Unit)
                val type = EnumType(typeInformation.name, values)
                setOf(type)
            }
        }
    }

    private fun toField(property: KSPropertyDeclaration): Field {
        return Field(
            name = property.simpleName.asString(),
            type = getType(property),
            multiValue = isMultiValued(property),
            optional = isOptional(property)
        )
    }

    private fun isOptional(property: KSPropertyDeclaration): Boolean {
        if (property.isOptional()) return true
        if (property.isNullable()) return true
        return false
    }

    private fun isMultiValued(property: KSPropertyDeclaration): Boolean {
        if (property.isArray()) return true
        if (property.isPrimitiveArray()) return true
        if (property.isCollection(resolver)) return true
        return false
    }

    private fun getType(property: KSPropertyDeclaration): String {
        val name = when {
            property.isArray() -> property.getInnerType().simpleName.asString()
            property.isPrimitiveArray() -> property.getInnerTypeNameOfPrimitiveArray()
            property.isOptional() -> property.getInnerType().simpleName.asString()
            property.isCollection(resolver) -> property.getInnerType().simpleName.asString()
            property.isNullable() -> property.type.resolve().declaration.simpleName.asString()
            else -> property.type.toString()
        }
        return sanitizeTypeString(name)
    }

    private fun sanitizeTypeString(name: String): String {
        return if (isBasicFieldType(name)) {
            sanitizeBasicFieldTypeName(name)
        } else {
            name
        }
    }

    private fun isBasicFieldType(name: String): Boolean {
        return BasicFieldType.values().map { it.id }.contains(sanitizeBasicFieldTypeName(name))
    }

    private fun sanitizeBasicFieldTypeName(name: String): String {
        return name.toLowerCase()
    }
}
