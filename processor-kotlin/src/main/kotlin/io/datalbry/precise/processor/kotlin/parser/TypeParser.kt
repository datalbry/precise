package io.datalbry.precise.processor.kotlin.parser

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSEmptyVisitor
import io.datalbry.precise.api.schema.type.Type
import io.datalbry.precise.processor.kotlin.visitor.PropertyVisitor

class TypeParser(
    private val resolver: Resolver,
    private val logger: KSPLogger
): KSEmptyVisitor<Unit, Set<Type>>() {

    private val properties = PropertyVisitor()

    override fun defaultHandler(node: KSNode, data: Unit): Set<Type> {

        // 1. Get all Fields
        val fields = node.accept(properties, Unit)

        // 3. FieldName to FieldTypes
        val

        // 4. If FieldType is ENUM or DATA CLASS recursion (If annotated with SchemaAware, BREAK)

        // 5. Fold all Fields

        // 6. ClassName + Type (Document OR Enum) and all Fields... RETURN
        TODO("Not yet implemented")
    }

}
