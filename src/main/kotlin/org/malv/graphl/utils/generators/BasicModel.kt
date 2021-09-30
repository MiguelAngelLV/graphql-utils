package org.malv.graphl.utils.generators

import org.hibernate.annotations.GenericGenerator
import org.springframework.data.domain.Persistable
import javax.persistence.*

@MappedSuperclass
public open class BasicModel() : Persistable<String> {

    @Id
    @Column(length = 36)
    @GeneratedValue(generator = "uuid", strategy = GenerationType.SEQUENCE)
    @GenericGenerator(name="uuid", strategy = "org.malv.graphl.utils.generators.UUIDGenerator")
    private var id: String = ""


    override fun getId(): String = id
    public fun setId(id: String) { this.id = id }
    override fun isNew(): Boolean = id.isBlank()
    override fun hashCode(): Int = id.hashCode()
    override fun equals(other: Any?): Boolean = if (other is BasicModel) other.id == id else false



    public constructor(id: String):this() {
        this.id = id
    }

}
