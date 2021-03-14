package io.datalbry.precise.processor.kotlin.visitor

import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSDefaultVisitor
import io.datalbry.precise.api.schema.type.Type
import io.datalbry.precise.api.schema.type.Types
import java.lang.IllegalArgumentException

/**
 * [KSDefaultVisitor] implementation which searches for a single [Type] by [KSClassDeclaration]
 *
 * @author timo gruen - 2021-03-11
 */
class FindTypeVisitor: KSDefaultVisitor<Unit, Type>() {

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit): Type {
        return object : Type {
            override val name = getName(classDeclaration)
            override val type = getType(classDeclaration)
        }
    }

    override fun defaultHandler(node: KSNode, data: Unit): Type {
        throw NotImplementedError("Only visitClassDeclaration() is supported")
    }

    private fun getName(classDeclaration: KSClassDeclaration): String {
        return classDeclaration.simpleName.asString()
    }

    private fun getType(classDeclaration: KSClassDeclaration): Types {
        return when (classDeclaration.classKind) {
            ClassKind.CLASS -> Types.DOCUMENT
            ClassKind.ENUM_CLASS -> Types.ENUM
            else -> throw IllegalArgumentException("ClassKind[${classDeclaration.classKind}] is not supported")
        }
    }
}
