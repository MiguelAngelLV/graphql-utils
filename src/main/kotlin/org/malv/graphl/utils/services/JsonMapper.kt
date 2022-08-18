package org.malv.graphl.utils.services

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component

@Component
public class JsonMapper(mapper: ObjectMapper) {


    init {
        MAPPER = mapper
    }

    public companion object {

        public lateinit var MAPPER: ObjectMapper

        public val mapper: ObjectMapper
            get() = MAPPER

    }


}


