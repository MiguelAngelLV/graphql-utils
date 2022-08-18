package org.malv.graphl.utils.services

import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import org.malv.graphl.utils.json.MapperCreator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.swing.text.Position

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


