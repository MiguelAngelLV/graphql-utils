package org.malv.graphl.utils.models

import org.malv.graphl.utils.primeng.Pagination
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import java.lang.Integer.min

public object PageList {


    public fun <T> create(list: List<T>, pagination: Pagination): Page<T> {

        val start = pagination.page * pagination.size
        val end = min((pagination.page + 1) * pagination.size, list.size)

        if (pagination.size <= start)
            return PageImpl(emptyList(), pagination.pagination, list.size.toLong())


        return PageImpl(list.subList(start, end), pagination.pagination, list.size.toLong())

    }

}
