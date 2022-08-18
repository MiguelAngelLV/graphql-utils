package org.malv.graphl.utils.services

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.malv.codapa.backend.utils.field
import org.malv.graphl.utils.Input
import org.malv.graphl.utils.graphqlErrors
import org.malv.graphl.utils.json.MapperCreator
import org.malv.graphl.utils.json.update
import org.malv.graphl.utils.tryOrNull
import java.lang.reflect.ParameterizedType
import javax.validation.Validation

public abstract class ValidationService<T> : JsonDeserializer<T>()  {


    private val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<*>

    public abstract fun findById(id: Long): T?



    public open fun update(update: Input, element: T) {
        JsonMapper.mapper.update(element, update)
        validate(element)
    }


    public open fun validate(element: T) {
        val factory = Validation.buildDefaultValidatorFactory()
        val validator = factory.validator
        val validate = validator.validate(element)

        if (validate.isEmpty()) return

        throw graphqlErrors(validate.associateBy({it.field}, {it.message}))

    }

    override fun deserialize(parser: JsonParser, ctxt: DeserializationContext): T? {


        val id = tryOrNull { parser.longValue } ?: return MapperCreator.mapper.readValue(parser, type) as? T

        return findById(id)

    }

}


