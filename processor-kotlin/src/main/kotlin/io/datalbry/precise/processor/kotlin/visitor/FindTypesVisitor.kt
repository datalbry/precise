package io.datalbry.precise.processor.kotlin.visitor

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.visitor.KSEmptyVisitor
import io.datalbry.precise.api.schema.field.Field
import io.datalbry.precise.api.schema.type.DocumentType
import io.datalbry.precise.api.schema.type.EnumType
import io.datalbry.precise.api.schema.type.Type
import io.datalbry.precise.api.schema.type.Types

/**
 * [KSPropertyDeclaration] implementation which searches for [Type]s
 *
 * @author timo gruen - 2021-03-11
 */
class FindTypesVisitor(private val logger: KSPLogger): KSEmptyVisitor<Unit, Set<Type>>() {

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
            property.simpleName.getQualifier(),
            property.type.toString()
        )
    }
}
