
package org.malv.graphl.utils.json


import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder


public object MapperCreator {



    public val mapper: ObjectMapper = create()


    public fun create(factory: JsonFactory = JsonFactory()): ObjectMapper {
        val builder = Jackson2ObjectMapperBuilder()
        val mapper = ObjectMapper(factory)

        builder.configure(mapper)

        return mapper
    }




    public fun createTest(): ObjectMapper {

        val builder = Jackson2ObjectMapperBuilder()
        builder.featuresToDisable(MapperFeature.USE_ANNOTATIONS)

        return  builder.build()
    }
}



