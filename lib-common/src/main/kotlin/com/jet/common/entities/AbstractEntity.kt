package com.jet.common.entities

import org.springframework.data.util.ProxyUtils
import java.io.Serializable
import java.util.*
import javax.persistence.MappedSuperclass
import javax.persistence.Transient

@MappedSuperclass
abstract class AbstractEntity<T : Serializable> {
    @get:Transient
    abstract val id: T?

    override fun hashCode(): Int {
        var hashCode = 17
        hashCode += when (id) {
            null -> 0
            else -> id.hashCode() * 31
        }
        return hashCode
    }

    override fun equals(other: Any?): Boolean {
        if (null == other) return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false

        val that = other as AbstractEntity<*>
        return Objects.equals(id, that.id)
    }

    override fun toString(): String {
        return "${javaClass.canonicalName}(id=$id)"
    }
}
