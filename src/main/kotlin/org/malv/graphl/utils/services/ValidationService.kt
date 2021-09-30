package org.malv.graphl.utils.services

import com.malv.codapa.backend.utils.field
import com.malv.codapa.backend.utils.text
import com.malv.codapa.backend.utils.update
import org.malv.graphl.utils.Input
import org.malv.graphl.utils.graphqlErrors
import org.malv.graphl.utils.json.MapperCreator
import javax.validation.Validation

public open class ValidationService<T> {


    public open fun update(update: Input, element: T) {
        val mapper = MapperCreator.create()
        update.remove("id")
        mapper.update(element, update)
        validate(element)
    }


    public open fun validate(element: T) {
        val factory = Validation.buildDefaultValidatorFactory()
        val validator = factory.validator
        val validate = validator.validate(element)

        if (validate.isEmpty()) return

        throw graphqlErrors(validate.associateBy({it.field}, {it.message}))

    }



}
