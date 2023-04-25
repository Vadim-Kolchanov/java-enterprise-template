package com.jet.common.entities

import java.util.*
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class UUIDAbstractEntity(
    @Id
    public override val id: UUID
) : AbstractEntity<UUID>()
