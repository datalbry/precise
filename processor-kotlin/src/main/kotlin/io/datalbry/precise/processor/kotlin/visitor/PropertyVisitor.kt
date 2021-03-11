package io.datalbry.precise.processor.kotlin.visitor

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.visitor.KSDefaultVisitor

class PropertyVisitor: KSDefaultVisitor<Unit, List<KSPropertyDeclaration>>() {

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) =
        classDeclaration.getAllProperties().filter { it.getter != null }

    override fun defaultHandler(node: KSNode, data: Unit) = emptyList<KSPropertyDeclaration>()
}
