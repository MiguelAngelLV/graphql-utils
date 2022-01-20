package org.malv.graphl.utils.json

import com.fasterxml.jackson.databind.ObjectMapper

public fun <T> ObjectMapper.update(value: T, path: Map<String, Any>) {
    readerForUpdating(value).readValue<T>(writeValueAsString(path))
}
