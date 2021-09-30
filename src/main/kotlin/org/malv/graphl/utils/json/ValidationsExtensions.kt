package com.malv.codapa.backend.utils

import javax.validation.ConstraintViolation

public val <T> ConstraintViolation<T>.field: String
    get() =
        if (message.contains('#'))
            message.substringAfter('#')
        else
            propertyPath.toString()


public val <T> ConstraintViolation<T>.text: String
    get() =
        if (message.contains('#'))
            message.substringBefore('#')
        else
            message


