package org.malv.graphl.utils.primeng

import com.querydsl.core.types.dsl.*
import khronos.beginningOfDay
import khronos.endOfDay
import java.text.SimpleDateFormat
import java.util.*

public class Filter(
    public val field: String,
    public val value: String?,
    public val matchMode: String?,
    public val operator: String?
) {


    public val valueList: List<String> by lazy {
        value?.removePrefix("[")?.removeSuffix("]")?.split(", ") ?: emptyList()
    }

    public fun filter(path: StringPath): BooleanExpression? {

        if (value == null) return null

        val list = value
            .removePrefix("[")
            .removeSuffix("]")
            .split(", ")

        return when (matchMode) {
            "startsWith" -> path.startsWithIgnoreCase(value)
            "endsWith" -> path.endsWithIgnoreCase(value)
            "contains" -> path.containsIgnoreCase(value)
            "equals" -> path.equalsIgnoreCase(value)
            "notStartWith" -> path.startsWithIgnoreCase(value).not()
            "notEndWitch" -> path.endsWithIgnoreCase(value).not()
            "notContains" -> path.containsIgnoreCase(value).not()
            "notEquals" -> path.equalsIgnoreCase(value).not()
            "in" -> path.`in`(list)
            else -> null
        }
    }


    public fun filter(path: NumberPath<Int>): BooleanExpression? {
        val value = value ?: return null

        val single = value.toIntOrNull()
        val list = value.split(", ").mapNotNull { it.toIntOrNull() }

        return when (matchMode) {
            "gt" -> path.gt(single)
            "gte" -> path.goe(single)

            "lt" -> path.lt(single)
            "lte" -> path.loe(single)

            "equals" -> path.eq(single)
            "notEquals" -> path.ne(single)

            "contains" -> path.`in`(list)

            else -> null
        }

    }

    public fun <T : Enum<T>?> filter(path: EnumPath<T>, converter: (String) -> T): BooleanExpression? {
        val value = value ?: return null

        val single = runCatching { converter(value) }.getOrNull()
        val list = valueList.mapNotNull { runCatching { converter(it) }.getOrNull() }

        return when (matchMode) {

            "equals" -> path.eq(single)
            "notEquals" -> path.ne(single)

            "contains" -> path.`in`(list)

            else -> null
        }

    }

    public fun filter(path: BooleanPath): BooleanExpression? {
        val value = value ?: return null
        val single = value.toBoolean()
        return when (matchMode) {
            "equals" -> path.eq(single)
            "notEquals" -> path.ne(single)
            else -> null
        }
    }

    public fun filter(path: DateTimePath<Date>): BooleanExpression? {
        if (value == null) return null

        val value = dateParser.parse(value)


        return when(matchMode) {
            "dateIs" -> path.between(value.beginningOfDay, value.endOfDay)
            "dateIsNot" -> path.notBetween(value.beginningOfDay, value.endOfDay)
            "dateBefore" -> path.before(value)
            "dateAfter" -> path.after(value)
            else -> null
        }

    }


    public fun filterLong(path: NumberPath<Long>): BooleanExpression? {

        val value = value ?: return  null

        if (value == "[]") return null


        val single = value.toLongOrNull()

        val list = valueList.map { it.toLong() }.toList()


        return when (matchMode) {
            "gt" -> path.gt(single)
            "gte" -> path.goe(single)

            "lt" -> path.lt(single)
            "lte" -> path.loe(single)

            "equals" -> path.eq(single)
            "notEquals" -> path.ne(single)

            "contains" -> path.`in`(list)

            else -> null
        }

    }

    public fun filter(path: DatePath<Date>): BooleanExpression? {
        if (value == null) return null

        val value = dateParser.parse(value)


        return when(matchMode) {
            "dateIs" -> path.between(value.beginningOfDay, value.endOfDay)
            "dateIsNot" -> path.notBetween(value.beginningOfDay, value.endOfDay)
            "dateBefore" -> path.before(value)
            "dateAfter" -> path.after(value)
            else -> null
        }
    }


    public val isValid: Boolean
        get() = value != null && value != "[]"


    private companion object {
        private val dateParser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
    }
}
