package io.datalbry.precise.core.schema.factory

import io.datalbry.precise.api.schema.field.BasicFieldType
import io.datalbry.precise.api.schema.type.EnumType
import io.datalbry.precise.api.schema.type.RecordType
import io.datalbry.precise.core.items.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ReflectionSchemaFactoryTest {

    @Test
    fun deriveSchema_fromJavaItem_fieldIsPresentAndDerivedCorrectly() {
        val schemaFactory = ReflectionSchemaFactory()
        val schema = schemaFactory.deriveSchema(JavaItem::class.java)

        val typeSchema = schema.types.firstOrNull { it.name == JavaItem::class.simpleName }
        assertTrue(typeSchema != null)
        assertTrue(typeSchema is RecordType)
        typeSchema as RecordType

        val nameSchema = typeSchema.fields.first { it.name == "name" }
        assertFalse(nameSchema.multiValue)
        assertTrue(nameSchema.optional)
        assertTrue(nameSchema.type == BasicFieldType.STRING.id)

        val numberSchema = typeSchema.fields.first { it.name == "number" }
        assertFalse(numberSchema.multiValue)
        assertFalse(numberSchema.optional)
        assertTrue(numberSchema.type == BasicFieldType.INT.id)
    }

    @Test
    fun deriveSchema_fromSimpleItem_fieldIsPresentAndDerivedCorrectly() {
        val schemaFactory = ReflectionSchemaFactory()
        val schema = schemaFactory.deriveSchema(SimpleItem::class.java)

        val typeSchema = schema.types.firstOrNull { it.name == SimpleItem::class.simpleName }
        assertTrue(typeSchema != null)
        assertTrue(typeSchema is RecordType)
        typeSchema as RecordType

        val fieldSchema = typeSchema.fields.first { it.name == SimpleItem::name.name }
        assertFalse(fieldSchema.multiValue)
        assertFalse(fieldSchema.optional)
        assertEquals(BasicFieldType.STRING.id, fieldSchema.type)
        assertEquals(SimpleItem::name.name, fieldSchema.name)
    }

    @Test
    fun deriveSchema_fromItemWithNestedRecord_typeIsDerivedCorrectly() {
        val schemaFactory = ReflectionSchemaFactory()
        val schema = schemaFactory.deriveSchema(NestedItem::class.java)

        val typeSchema = schema.types.firstOrNull { it.name == NestedItem::class.simpleName }
        assertTrue(typeSchema != null)
        assertTrue(typeSchema is RecordType)
        typeSchema as RecordType

        val nameSchema = typeSchema.fields.first { it.name == NestedItem::name.name }
        assertFalse(nameSchema.multiValue)
        assertFalse(nameSchema.optional)
        assertEquals(BasicFieldType.STRING.id, nameSchema.type)
        assertEquals(SimpleItem::name.name, nameSchema.name)

        val authorSchema = typeSchema.fields.first { it.name == NestedItem::author.name }
        assertFalse(authorSchema.multiValue)
        assertFalse(authorSchema.optional)
        assertEquals(Person::class.simpleName, authorSchema.type)
        assertEquals(NestedItem::author.name, authorSchema.name)
    }

    @Test
    fun deriveSchema_redundantClassParameter_correspondingTypeIsDerivedOnce() {
        val schemaFactory = ReflectionSchemaFactory()
        val schema = schemaFactory.deriveSchema(NestedItem::class.java, Person::class.java)

        val typeSchema = schema.types.filter { it.name == Person::class.simpleName }
        assertTrue(typeSchema.size == 1)
    }

    @Test
    fun deriveSchema_fromItemWithNestedRecord_nestedTypeIsDerivedAsWell() {
        val schemaFactory = ReflectionSchemaFactory()
        val schema = schemaFactory.deriveSchema(NestedItem::class.java)

        val typeSchema = schema.types.firstOrNull { it.name == Person::class.simpleName }
        assertTrue(typeSchema != null)
        assertTrue(typeSchema is RecordType)
        typeSchema as RecordType

        val nameField = typeSchema.fields.first { it.name == Person::name.name }
        assertFalse(nameField.multiValue)
        assertFalse(nameField.optional)
        assertEquals(BasicFieldType.STRING.id, nameField.type)
        assertEquals(SimpleItem::name.name, nameField.name)

        val emailField = typeSchema.fields.first { it.name == Person::email.name }
        assertFalse(emailField.multiValue)
        assertFalse(emailField.optional)
        assertEquals(BasicFieldType.STRING.id, emailField.type)
        assertEquals(Person::email.name, emailField.name)
    }

    @Test
    fun deriveSchema_fromSimpleEnum_defaultValuesArePresent() {
        val schemaFactory = ReflectionSchemaFactory()
        val schema = schemaFactory.deriveSchema(SimpleEnum::class.java)

        val typeSchema = schema.types.firstOrNull { it.name == SimpleEnum::class.simpleName }
        assertTrue(typeSchema != null)
        assertTrue(typeSchema is EnumType)
        typeSchema as EnumType

        val enumValues = SimpleEnum.values().map { it.name }
        assertTrue(typeSchema.values.containsAll(enumValues))
    }

    @Test
    fun deriveSchema_fromItemWithArrayField_arrayFieldIsMultiValued() {
        val schemaFactory = ReflectionSchemaFactory()
        val schema = schemaFactory.deriveSchema(ItemWithArrayField::class.java)

        val typeSchema = schema.types.firstOrNull { it.name == ItemWithArrayField::class.simpleName }
        assertTrue(typeSchema != null)
        assertTrue(typeSchema is RecordType)
        typeSchema as RecordType

        val contributorsSchema = typeSchema.fields.firstOrNull { it.name == ItemWithArrayField::contributors.name }
        assertTrue(contributorsSchema != null)
        assertTrue(contributorsSchema!!.multiValue)
        assertFalse(contributorsSchema.optional)
    }

    @Test
    fun deriveSchema_fromItemWithCollectionField_arrayFieldIsMultiValued() {
        val schemaFactory = ReflectionSchemaFactory()
        val schema = schemaFactory.deriveSchema(ItemWithCollectionField::class.java)

        val typeSchema = schema.types.firstOrNull { it.name == ItemWithCollectionField::class.simpleName }
        assertTrue(typeSchema != null)
        assertTrue(typeSchema is RecordType)
        typeSchema as RecordType

        val contributorsSchema = typeSchema.fields.firstOrNull { it.name == ItemWithCollectionField::contributors.name }
        assertTrue(contributorsSchema != null)
        assertTrue(contributorsSchema!!.multiValue)
        assertFalse(contributorsSchema.optional)
    }

    @Test
    fun deriveSchema_fromItemWithExcludedField_excludedFieldsAreNotPresentInSchema() {
        val schemaFactory = ReflectionSchemaFactory()
        val schema = schemaFactory.deriveSchema(ItemWithExcludedField::class.java)

        val typeSchema = schema.types.firstOrNull { it.name == ItemWithExcludedField::class.simpleName }
        assertTrue(typeSchema != null)
        assertTrue(typeSchema is RecordType)
        typeSchema as RecordType

        val contributorsSchema = typeSchema.fields.firstOrNull { it.name == ItemWithExcludedField::title.name }
        assertTrue(contributorsSchema != null)
        assertFalse(contributorsSchema!!.multiValue)
        assertFalse(contributorsSchema.optional)
        assertEquals(BasicFieldType.STRING.id, contributorsSchema.type )

        assertTrue(typeSchema.fields.none { it.name == ItemWithExcludedField::id.name })
    }

}
