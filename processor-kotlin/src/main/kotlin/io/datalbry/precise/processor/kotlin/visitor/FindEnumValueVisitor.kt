package io.datalbry.precise.processor.kotlin.visitor

import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSDefaultVisitor

/**
 * [KSDefaultVisitor] implementation which searches for the values of an enum
 *
 * Searches for [ClassKind.ENUM_ENTRY] and returns their simpleName
 *
 * @author timo gruen - 2021-03-11
 */
class FindEnumValueVisitor: KSDefaultVisitor<Unit, Set<String>>() {

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit): Set<String> {
        return classDeclaration
            .declarations
            .filterIsInstance<KSClassDeclaration>()
            .filter { it.classKind == ClassKind.ENUM_ENTRY }
            .map { it.simpleName.asString() }
            .toSet()
    }

    override fun defaultHandler(node: KSNode, data: Unit) = emptySet<String>()
}

