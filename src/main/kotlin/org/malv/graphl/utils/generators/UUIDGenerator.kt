package org.malv.graphl.utils.generators

import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator
import java.io.Serializable
import java.util.*

public class UUIDGenerator : IdentifierGenerator {


    override fun generate(session: SharedSessionContractImplementor, element: Any): Serializable {

        if (element is BasicModel && !element.isNew)
            return element.id


        return UUID.randomUUID().toString()
    }
}
