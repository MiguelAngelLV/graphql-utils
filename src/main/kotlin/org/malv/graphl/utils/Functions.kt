package org.malv.graphl.utils

import com.querydsl.core.types.Predicate
import graphql.GraphqlErrorException
import org.springframework.data.querydsl.QuerydslPredicateExecutor


public fun graphqlError(description: String): Throwable {
    throw GraphqlErrorException.newErrorException().message(description).build()
}

public fun graphqlErrors(errors: Map<String, String>): Throwable {
    val first = errors.entries.first()




    throw GraphqlErrorException.newErrorException().message("${first.key}: ${first.value}").build()
}

public fun <T> graphqlNotFound(id: T): Throwable {
    throw graphqlError("$id not found")
}


public typealias Input = HashMap<String, Any>

public val Input.isNew: Boolean
    get() = get("id") == null

public fun <T> Input.getId(): T {
    return get("id") as T
}

public fun Input.removeEmpty(key: String) {

    val value = getOrDefault(key, "").toString()

    if (value.isBlank())
        remove(key)


}


public fun <T> QuerydslPredicateExecutor<T>.findOneOrNull(predicate: Predicate): T? {
    return findOne(predicate).orElse(null)
}


public fun <T> tryOrNull(operation: () -> T): T? {

    return try {
        operation()
    } catch (e: Exception) {
        null
    }

}
