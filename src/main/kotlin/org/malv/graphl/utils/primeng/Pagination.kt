package org.malv.graphl.utils.primeng

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.dsl.*
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.util.*

public class Pagination(

    public var page: Int = 0,
    public var size: Int = Int.MAX_VALUE,
    public var direction: Int = -1,
    public val columns: String? = null,
    public val filters: MutableList<Filter> = ArrayList()

) {


    public val query: BooleanBuilder = BooleanBuilder()

    public val pagination: PageRequest
        get() {

            if (columns == null)
                return PageRequest.of(page, size)

            val c = columns.split(",")

            val sort = if (direction == 1)
                Sort.by(Sort.Direction.ASC, *c.toTypedArray())
            else
                Sort.by(Sort.Direction.DESC, *c.toTypedArray())


            return PageRequest.of(page, size, sort)
        }


    public fun filter(path: NumberPath<Int>) {
        val field = "$path".substringAfter(".")

        filters
            .filter { it.field == field }
            .filter { it.isValid }
            .mapNotNull { it.filter(path) }
            .forEach {
                query.and(it)
            }

    }


    public fun filter(path: StringPath) {
        val field = "$path".substringAfter(".")

        filters
            .filter { it.field == field }
            .filter { it.isValid }
            .mapNotNull { it.filter(path) }
            .forEach {
                query.and(it)
            }

    }

    public fun rawFilter(field: String): String? {
        return filters.firstOrNull { it.field == field }?.value
    }

    public fun addFilter(field: String, value: Any, matchMode: String? = null) {
        filters.add(Filter(field, value.toString(), matchMode, null))
    }

    public fun addFilter(filter: BooleanExpression) {
        query.and(filter)
    }

    public fun <T : Enum<T>?> filter(path: EnumPath<T>, converter: (String) -> T) {
        val field = "$path".substringAfter(".")

        filters
            .filter { it.field == field }
            .filter { it.isValid }
            .mapNotNull { it.filter(path, converter) }
            .forEach {
                query.and(it)
            }

    }

    public fun filter(path: BooleanPath) {
        val field = "$path".substringAfter(".")

        filters
            .filter { it.field == field }
            .filter { it.isValid }
            .mapNotNull { it.filter(path) }
            .forEach {
                query.and(it)
            }
    }


    public fun filter(path: DateTimePath<Date>) {
        val field = "$path".substringAfter(".")

        filters
            .filter { it.field == field }
            .filter { it.isValid }
            .mapNotNull { it.filter(path) }
            .forEach {
                query.and(it)
            }
    }


    public fun filter(path: DatePath<Date>) {
        val field = "$path".substringAfter(".")

        filters
            .filter { it.field == field }
            .filter { it.isValid }
            .mapNotNull { it.filter(path) }
            .forEach {
                query.and(it)
            }
    }


    public fun filterLong(path: NumberPath<Long>) {
        val field = "$path".substringAfter(".")

        filters
            .filter { it.field == field }
            .filter { it.isValid }
            .mapNotNull { it.filterLong(path) }
            .forEach {
                query.and(it)
            }

    }


    public fun customFilter(field: String, operation: (value: String, BooleanBuilder) -> Unit) {

        filters
            .filter { it.field == field }
            .filter { it.isValid }
            .mapNotNull { it.value }
            .forEach {
                operation(it, query)
            }

    }

    public fun customFilterLong(field: String, operation: (value: Long, BooleanBuilder) -> Unit) {
        filters
            .filter { it.field == field }
            .filter { it.isValid }
            .mapNotNull { it.value?.toLongOrNull() }
            .forEach {
                operation(it, query)
            }
    }


    public fun customFilterInt(field: String, operation: (value: Int, BooleanBuilder) -> Unit) {
        filters
            .filter { it.field == field }
            .filter { it.isValid }
            .mapNotNull { it.value?.toIntOrNull() }
            .forEach {
                operation(it, query)
            }
    }

    public fun customFilterList(field: String, operation: (value: List<String>, BooleanBuilder) -> Unit) {

        filters
            .filter { it.field == field }
            .filter { it.valueList.isNotEmpty() }
            .forEach {
                operation(it.valueList, query)
            }

    }


    public fun global(vararg path: StringPath) {

        val global = filters.firstOrNull { it.field == "global" } ?: return
        val value = global.value ?: return

        value.split(" ").forEach { v ->
            query.andAnyOf(*(path.map { it.containsIgnoreCase(v) }.toTypedArray()))
        }
    }

}
