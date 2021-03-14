package io.datalbry.precise.processor.kotlin.extension

import com.google.devtools.ksp.symbol.KSDeclaration

/**
 * Short circuit to check whether the [KSDeclaration] has the name [pkg]
 *
 * @param pkg of the KSDeclaration
 *
 * @return true if the [KSDeclaration] lies in the package, else false
 */
fun KSDeclaration.hasPackage(pkg: String): Boolean {
    return this.packageName.asString() == pkg
}

/**
 * Short circuit to check whether the [KSDeclaration] has the name [name]
 *
 * @param name of the KSDeclaration
 *
 * @return true if the [KSDeclaration] has the name, else false
 */
fun KSDeclaration.hasName(name: String): Boolean {
    return this.simpleName.asString() == name
}
